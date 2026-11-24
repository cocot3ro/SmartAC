import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
}

val generateBuildConfig = tasks.register("generateBuildConfig") {
    group = "build"
    description = "Generates BuildConfig with project information."

    val outputDir = layout.buildDirectory.dir("generated/source/buildConfig/main/kotlin")
    val buildVersion = providers.gradleProperty("serverSiteVersion").get()

    outputs.dir(outputDir)

    doLast {
        val file = outputDir.get().asFile

        file.parentFile.mkdirs()

        FileSpec.builder("com.cocot3ro.smartac.generated", "BuildConfig")
            .addType(
                TypeSpec.objectBuilder("BuildConfig")
                    .addProperty(
                        PropertySpec
                            .builder("VERSION", String::class)
                            .addModifiers(KModifier.CONST)
                            .initializer("%S", buildVersion)
                            .build()
                    )
                    .build()
            )
            .build()
            .writeTo(file)
    }
}

kotlin {
    jvm()

    js {
        browser()
        binaries.library()
    }

    sourceSets {
        commonMain {

            kotlin.srcDir(generateBuildConfig)

            dependencies {
                api(projects.core)

                implementation(libs.kotlinx.serialization.core)

                implementation(ktorLibs.serialization.kotlinx.json)
                implementation(ktorLibs.resources)
            }
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
