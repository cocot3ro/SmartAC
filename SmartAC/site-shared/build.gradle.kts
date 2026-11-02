plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()

    js {
        browser()
        binaries.library()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.core)

            implementation(ktorLibs.serialization.kotlinx.json)
            implementation(ktorLibs.resources)
        }


        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
