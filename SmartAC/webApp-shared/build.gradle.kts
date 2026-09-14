plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()

    js {
        outputModuleName = "webApp-shared"
        browser()
        binaries.library()
        generateTypeScriptDefinitions()
        compilerOptions {
            target = "es2015"
            optIn.add("kotlin.js.ExperimentalJsExport")
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.core)
        }


        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
