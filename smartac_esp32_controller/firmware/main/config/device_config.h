#ifndef FIRMWARE_DEVICE_CONFIG_H
#define FIRMWARE_DEVICE_CONFIG_H

#include <string>

class DeviceConfig {
public:
    static DeviceConfig &instance();

    [[nodiscard]] static const char *get_api_key()  { return instance().api_key.c_str(); }
    [[nodiscard]] static const char *get_device_code()  { return instance().device_code.c_str(); }
    [[nodiscard]] static const char *get_proof_of_possession()  { return instance().prov_pop.c_str(); }
    [[nodiscard]] static const char *get_backend_host()  { return instance().backend_host.c_str(); }
    [[nodiscard]] static const char *get_mqtt_host()  { return instance().mqtt_host.c_str(); }
    [[nodiscard]] static uint32_t get_mqtt_port()  { return instance().mqtt_port; }
    [[nodiscard]] static const char *get_mqtt_user()  { return instance().mqtt_user.c_str(); }
    [[nodiscard]] static const char *get_mqtt_password()  { return instance().mqtt_password.c_str(); }

private:
    std::string api_key;
    std::string device_code;
    std::string prov_pop;
    std::string backend_host;
    std::string mqtt_host;
    uint32_t mqtt_port;
    std::string mqtt_user;
    std::string mqtt_password;

    DeviceConfig();
};

#endif //FIRMWARE_DEVICE_CONFIG_H
