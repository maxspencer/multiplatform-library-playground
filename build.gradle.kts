import java.net.URI

plugins {
    kotlin("multiplatform") version "1.4.31"
    id("com.android.library")
    id("maven-publish")
}

group = "com.gu.kotlin"
version = "0.1-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

kotlin {
    android {
        publishLibraryVariants("release")
    }
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

afterEvaluate {
    publishing {
        publications
            .filterIsInstance<MavenPublication>()
            .forEach { publication ->
                publication.pom {
                    name.set("multiplatform-playground")
                    description.set("A Kotlin Multiplatform library for experiments.")
                    url.set("https://github.com/maxspencer/multiplatform-library-playground")
                    licenses {
                        license {
                            name.set("The MIT License (MIT)")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }
                    scm {
                        connection.set("scm:git:git://github.com/maxspencer/multiplatform-library-playground.git")
                        developerConnection.set("scm:git:ssh://github.com/maxspencer/multiplatform-library-playground.git")
                        url.set("https://github.com/maxspencer/multiplatform-library-playground")

                    }
                    developers {
                        developer {
                            id.set("maxspencer")
                            name.set("Max Spencer")
                            email.set("max.spencer@guardian.co.uk")
                            url.set("https://github.com/maxspencer")
                            organization.set("The Guardian")
                            organizationUrl.set("https://theguardian.com")
                        }
                    }
                }
            }
        repositories {
            maven {
                name = "snapshot"
                url = URI.create("https://oss.sonatype.org/content/repositories/snapshots/")
                credentials {
                    username = (properties["ossrhUsername"] as? String
                        ?: System.getenv("OSSRH_USERNAME")).also {
                        println(it)
                    }
                    password = (properties["ossrhPassword"] as? String
                        ?: System.getenv("OSSRH_PASSWORD")).also {
                        println(it)
                    }
                }
            }
            maven {
                name = "staging"
                url = URI.create("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = properties["ossrhUsername"] as? String
                        ?: System.getenv("OSSRH_USERNAME")
                    password = properties["ossrhPassword"] as? String
                        ?: System.getenv("OSSRH_PASSWORD")
                }
            }
        }
    }
    //signing {
    //    sign(publishing.publications)
    //}
}