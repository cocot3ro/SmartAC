idf_component_register(
        SRCS "main.cpp" "ota/ota.cpp" "ota/version.cpp" "config/device_config.cpp"
        "mqtt/mqtt_manager.cpp"
        "display/display_frame_t.cpp" "display/display_reader.cpp"
        "ir/ir_frame_t.cpp" "ir/ir_emitter.cpp"

        INCLUDE_DIRS "." "ota" "mqtt" "config" "display" "ir"

        PRIV_REQUIRES mqtt nvs_flash esp_wifi esp_event esp_driver_gpio esp_driver_uart esp_https_ota app_update esp_http_server esp_http_client
)
