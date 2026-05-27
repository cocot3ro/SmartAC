import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.koin.compiler)
}

kotlin {

    android {
        namespace = "dev.cocot3ro.smartac.app.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }

        androidResources {
            enable = true
        }

        withHostTest {
            isIncludeAndroidResources = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.core)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.process)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(ktorLibs.client.core)
            implementation(ktorLibs.client.logging)
            implementation(ktorLibs.client.contentNegotiation)
            implementation(ktorLibs.client.resources)
            implementation(ktorLibs.client.websockets)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.bundles.koin.client)

            implementation(libs.ksafe)

            implementation(libs.androidx.navigation3.runtime)
            implementation(libs.androidx.navigation3.ui)
            implementation(libs.androidx.lifecycle.viewmodel.navigation3)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(ktorLibs.client.okhttp)

            implementation(libs.koin.android)
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}
