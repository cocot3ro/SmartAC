idf_component_register(
        SRCS "main.cpp" "MqttManager.cpp" "DisplayReader.cpp" "IrSender.cpp"
        INCLUDE_DIRS "."
        PRIV_REQUIRES mqtt nvs_flash esp_wifi esp_event esp_driver_gpio esp_driver_uart
)
