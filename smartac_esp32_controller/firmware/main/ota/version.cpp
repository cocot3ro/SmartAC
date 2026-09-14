#include "version.h"

#include <cstdio>
#include <cstring>

bool Version::operator >(const Version &other) const {
    if (this->major != other.major)
        return this->major > other.major;

    if (this->minor != other.minor)
        return this->minor > other.minor;

    return this->patch > other.patch;
}

bool Version::parse(const char *str) {
    return 3 == sscanf( // NOLINT(*-err34-c)
               str,
               "%d.%d.%d",
               &this->major,
               &this->minor,
               &this->patch
           ) && this->major + this->minor + this->patch > 0;
}
