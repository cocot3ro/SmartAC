// Sniffer UART (solo RX) en ESP32
// RX -> GPIO4 (con level shifter 5V->3.3V entre display y ESP32 RX)
// GND común
// TX no usado

HardwareSerial ACSerial(2); // UART2

const int RX_PIN = 4;
const int BAUD = 1200;      // aprox. (bit ~830us)
const int FRAME_LEN = 25;   // 1 ADDRESS + 23 bytes + 1 checksum
const uint8_t SYNC_BYTE = 0xAA;

int ok_counter = 0;
int fail_counter = 0;

uint8_t buffer[64];
int bufPos = 0;

uint8_t compute_checksum(const uint8_t *data, int len) {
  uint32_t s = 0;
  for (int i = 0; i < len; ++i) s += data[i];
  return uint8_t(0xFF - (s & 0xFF));
}

void process_frame(const uint8_t *data, int len) {
  int payload_len = len - 1;
  uint8_t calc = compute_checksum(data, payload_len);
  uint8_t recv = data[payload_len];
  bool ok = (calc == recv);

  if (ok) ok_counter++;
  else fail_counter++;

  Serial.printf("Frame len=%d  checksum calc=0x%02X recv=0x%02X  %s  fail_counter=%d  ok_counter=%d\n",
                len, calc, recv, ok ? "OK" : "FAIL", fail_counter, ok_counter);

  Serial.print("Data: ");
  for (int i = 0; i < len; ++i) {
    Serial.printf("%02X ", data[i]);
  }
  Serial.println();
  Serial.println();
}

void sync_reader() {
  Serial.println("Sincronizando con byte 0xAA...");
  unsigned long start = millis();
  bool synced = false;

  // Espera hasta encontrar el byte de inicio 0xAA
  while (!synced && millis() - start < 3000) {  // timeout de 3 segundos
    if (ACSerial.available()) {
      uint8_t b = ACSerial.read();
      if (b == SYNC_BYTE) {
        synced = true;
        buffer[0] = b;
        bufPos = 1;
        Serial.println("Sincronización OK");
      }
    }
  }

  if (!synced) {
    Serial.println("No se detectó byte de sincronización (0xAA)");
  }
}

void setup() {
  Serial.begin(115200);
  delay(200);
  Serial.printf("Iniciando sniffer UART en RX=GPIO%d, baud=%d...\n", RX_PIN, BAUD);

  ACSerial.begin(BAUD, SERIAL_8N1, RX_PIN, -1);

  // Intentar sincronizar antes de leer
  sync_reader();
}

void loop() {
  while (ACSerial.available()) {
    int b = ACSerial.read();
    if (b < 0) break;
    buffer[bufPos++] = uint8_t(b);

    if (bufPos >= FRAME_LEN) {
      process_frame(buffer, bufPos);
      bufPos = 0;
    }

    if (bufPos >= (int)sizeof(buffer) - 1) bufPos = 0; // protección overflow
  }
}
