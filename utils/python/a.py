# Voy a repetir el análisis excluyendo el primer byte (los primeros 8 bits) de cada binData.
# Mis hipótesis:
# - El primer byte es preámbulo y debe quitarse antes de calcular el checksum.
# - Volveré a probar XOR, suma modular y CRC-8 (mis polinomios previos) sobre los 232 bits restantes (29 bytes).
# - Mantendré la regla de truncamiento de unos finales.
#
from typing import List, Tuple

frames = [
    ("001010101100000000010000100001000000000100111000010000000001000001010100001101010001010101000000000101010011110000000001000000000100000000010000000001000000000100000000010000000001000000000100000000010000000001", "001111010"),
    ("001010101100000000010000100001000000000100111000010000000001000001000100001101010001010101000000000100000000010000000001000000000100000000010000000001000000000100000000010000000001000000000100000000010000000001", "01100000"),
    ("001010101100000000010000100001000000000100111000010000000001000001000100001101010001010101000000000100000000010000000001000000000100000000010000000001000000000100000000010000000001000000000100000000010000000001", "01100000"),
    ("001010101100000000010000100001000000000100111000010000000001000001010100001101010001010101000000000101010011110000000001000000000100000000010000000001000000000100000000010000000001000000000100000000010000000001", "001111010"),
    ("001010101100000000010000100001000000000100101000010000000001000001010100001101010001010101000000000101010011110000000001000000000100000000010000000001000000000100000000010000000001000000000100000000010000000001", "001000110"),
    ("001010101100000000010000100001000000000100101000010000000001000001010100001101010001010101000000000101010011110000000001000000000100000000010000000001000000000100000000010000000001000000000100000000010000000001", "001000110"),
    ("001010101100000000110000100001000010100100101000010000000001000001000100001101010001010101000000000100000000010000000001000000000100000000010000000001000000000100000000010000000001000000000100000000010000000001", "0111110"),
]

def bits_to_bytes(bits: str, msb_first=True) -> bytes:
    b = []
    for i in range(0, len(bits), 8):
        chunk = bits[i:i+8]
        if not msb_first:
            chunk = chunk[::-1]
        b.append(int(chunk, 2))
    return bytes(b)

def bytes_to_bitstr(x: int, length: int) -> str:
    return format(x, '0{}b'.format(length))

def sum_mod(bytes_data: bytes, bits: int) -> int:
    s = sum(bytes_data)
    mask = (1 << bits) - 1
    return s & mask

def xor_bytes(bytes_data: bytes, bits: int) -> int:
    v = 0
    for bb in bytes_data:
        v ^= bb
    mask = (1 << bits) - 1
    return v & mask

def crc8(bytes_data: bytes, poly=0x07, init=0x00, xorout=0x00, refin=False, refout=False):
    crc = init & 0xFF
    for b in bytes_data:
        data = b
        if refin:
            data = int('{:08b}'.format(b)[::-1], 2)
        crc ^= data
        for _ in range(8):
            if crc & 0x80:
                crc = ((crc << 1) ^ poly) & 0xFF
            else:
                crc = (crc << 1) & 0xFF
    if refout:
        crc = int('{:08b}'.format(crc)[::-1], 2)
    crc ^= xorout
    return crc & 0xFF

def matches_with_trailing_ones_truncated(calc_bits: str, observed_bits: str) -> bool:
    if calc_bits == observed_bits:
        return True
    if len(calc_bits) >= len(observed_bits):
        prefix = calc_bits[:len(observed_bits)]
        suffix = calc_bits[len(observed_bits):]
        if prefix == observed_bits and all(ch=='1' for ch in suffix) and len(suffix) >= 1:
            return True
    return False

polys = [0x07, 0x31, 0x2F, 0x9B, 0x1D, 0x85]  # ampliar polinomios un poco
inits = [0x00, 0xFF]
xorouts = [0x00, 0xFF]
ref_opts = [(False, False), (True, True)]

results = []

for idx, (bits, obs) in enumerate(frames):
    obs = obs.strip()
    # excluir primer byte (8 bits)
    bits_trunc = bits[8:]
    if len(bits_trunc) != 232:
        print("Warning: expected 232 bits after truncating first byte, got", len(bits_trunc))
    found = []
    b_msb = bits_to_bytes(bits_trunc, msb_first=True)   # 29 bytes
    b_lsb = bits_to_bytes(bits_trunc, msb_first=False)
    for order_name, bdata in [('MSB-first bytes', b_msb), ('LSB-first bytes', b_lsb)]:
        # probar sum y xor para longitudes 1..9
        for L in range(1, 10):
            v_sum = sum_mod(bdata, L)
            v_xor = xor_bytes(bdata, L)
            s_sum = bytes_to_bitstr(v_sum, L)
            s_xor = bytes_to_bitstr(v_xor, L)
            if matches_with_trailing_ones_truncated(s_sum, obs):
                found.append((order_name, 'SUM_mod', L, s_sum, 'matches (with truncation rule)'))
            if matches_with_trailing_ones_truncated(s_xor, obs):
                found.append((order_name, 'XOR_bytes', L, s_xor, 'matches (with truncation rule)'))
        # probar CRC8 variantes
        for poly in polys:
            for init in inits:
                for xorout in xorouts:
                    for refin, refout in ref_opts:
                        crc = crc8(bdata, poly=poly, init=init, xorout=xorout, refin=refin, refout=refout)
                        for L in range(1,10):
                            mask = (1<<L)-1
                            lsb_part = crc & mask
                            msb_part = (crc >> (8-L)) & mask if L<=8 else crc & mask
                            s_lsb = bytes_to_bitstr(lsb_part, L)
                            s_msb = bytes_to_bitstr(msb_part, L)
                            if matches_with_trailing_ones_truncated(s_lsb, obs):
                                found.append((order_name, f'CRC8 poly=0x{poly:02X} init=0x{init:02X} xorout=0x{xorout:02X} refin={refin} refout={refout}', L, s_lsb, 'LSB-part matches (with truncation rule)'))
                            if matches_with_trailing_ones_truncated(s_msb, obs):
                                found.append((order_name, f'CRC8 poly=0x{poly:02X} init=0x{init:02X} xorout=0x{xorout:02X} refin={refin} refout={refout}', L, s_msb, 'MSB-part matches (with truncation rule)'))
    results.append((idx, obs, found))

# Mostrar resumen
for idx, obs, found in results:
    print(f"Frame #{idx+1}: observed checksum = {obs}")
    if not found:
        print("  -> No coincidencias encontradas con los métodos probados (excluyendo primer byte).")
    else:
        print(f"  -> {len(found)} coincidencia(s) (algoritmo, params, L, calculated_bits, note):")
        for f in found[:60]:
            print("     ", f)
    print()
