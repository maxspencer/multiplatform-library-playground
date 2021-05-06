plugins {
    kotlin("multiplatform") version "1.4.31"
    id("com.android.library")
}

group = "com.gu.kotlin"
version = "0.1-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

kotlin {
    android()
    iosX64("ios") {
        binaries {
            framework {
                baseName = "multiplatform-playground"
            }
        }
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.google.android.material:material:1.2.1")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13")
            }
        }
        val iosMain by getting
        val iosTest by getting
    }
}

android {
    compileSdkVersion(30)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(30)
    }
}