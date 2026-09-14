#!/usr/bin/env bash
set -e

PORT="${1:-/dev/ttyUSB0}"
NVS_SIZE="0x4000"

python "$IDF_PATH"/components/nvs_flash/nvs_partition_generator/nvs_partition_gen.py \
    generate nvs/devicecfg_dev.csv nvs/devicecfg_dev.bin $NVS_SIZE

idf.py -p "$PORT" partition-table-flash

parttool.py --port "$PORT" write_partition \
    --partition-name=devicecfg \
    --input nvs/devicecfg_dev.bin
