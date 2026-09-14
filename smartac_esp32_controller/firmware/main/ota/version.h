#ifndef FIRMWARE_VERSION_H
#define FIRMWARE_VERSION_H

struct Version {
    int major;
    int minor;
    int patch;

    [[nodiscard]] bool operator >(const Version &other) const;

    [[nodiscard]] bool parse(const char *str);
};

#endif //FIRMWARE_VERSION_H
