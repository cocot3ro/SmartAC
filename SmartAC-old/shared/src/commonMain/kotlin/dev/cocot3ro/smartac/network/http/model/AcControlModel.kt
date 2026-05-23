package dev.cocot3ro.smartac.network.http.model

import kotlinx.serialization.Serializable

@Serializable
data object AcControlModel {

    @Serializable
    enum class Power {
        POWER_ON,
        POWER_OFF,
        TOGGLE;
    }

    @Serializable
    enum class Mode {
        COOL,
        DRY,
        FAN,
        HEAT;
    }

    @Serializable
    enum class FanSpeed {
        AUTO,
        LOW,
        MEDIUM,
        HIGH;
    }

    @Serializable
    sealed class Temperature {
        @Serializable
        data class Set(val value: Int) : Temperature()

        @Serializable
        data object Increase : Temperature()

        @Serializable
        data object Decrease : Temperature()
    }
}
