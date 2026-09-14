#include "device_config.h"

#include "esp_check.h"
#include "nvs.h"

constexpr auto TAG = "CONFIG";

DeviceConfig::DeviceConfig() {
    nvs_handle_t nvs;

    ESP_ERROR_CHECK(
        nvs_open_from_partition(CONFIG_NVS_PARTITION_NAME, CONFIG_NVS_NAMESPACE, NVS_READONLY, &nvs)
    );

    auto readString = [&](const char *key) -> std::string {
        size_t len = 0;

        ESP_ERROR_CHECK(
            nvs_get_str(nvs, key, nullptr, &len)
        );

        std::string value(len - 1, '\0');

        ESP_ERROR_CHECK(
            nvs_get_str(nvs, key, value.data(), &len)
        );

        return value;
    };

    auto readU32 = [&](const char *key) -> uint32_t {
        uint32_t value = 0;
        ESP_ERROR_CHECK(
            nvs_get_u32(nvs, key, &value)
        );
        return value;
    };

    api_key = readString("api_key");
    device_code = readString("device_code");
    prov_pop = readString("prov_pop");
    backend_host = readString("backend_host");
    mqtt_host = readString("mqtt_host");
    mqtt_port = readU32("mqtt_port");
    mqtt_user = readString("mqtt_user");
    mqtt_password = readString("mqtt_password");

    nvs_close(nvs);
}

DeviceConfig &DeviceConfig::instance() {
    static DeviceConfig instance;
    return instance;
}
