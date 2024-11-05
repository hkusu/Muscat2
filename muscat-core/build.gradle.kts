import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "io.github.hkusu.muscat"
version = "0.20.2"

kotlin {
    // jvm()
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    // linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
                implementation(libs.coroutines.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "io.github.hkusu.musca.core"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    if (System.getenv("ORG_GRADLE_PROJECT_mavenCentralUsername") != null) {
        signAllPublications()
    }
    //signAllPublications()

    coordinates(group.toString(), "muscat-core", version.toString())

    pom {
        name = "Muscat"
        description = "Kotlin Multiplatform Flux framework."
        inceptionYear = "2024"
        url = "https://github.com/hkusu/Muscat"
        licenses {
            license {
                name = "MIT"
                url = "https://opensource.org/licenses/MIT"
                // distribution = "ZZZ"
            }
        }
        developers {
            developer {
                id = "hkusu"
                name = "Hiroyuki Kusu"
                // url = "ZZZ"
            }
        }
        scm {
            url = "https://github.com/hkusu/Muscat"
            // connection = "YYY"
            // developerConnection = "ZZZ"
        }
    }
}