plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.8.0"
}

group = "com.anas.intellij.plugins.ayah"
version = "0.0.8"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.anas-elgarhy:alquran-cloud-api:0.4.0-v1")
    implementation("com.miglayout:miglayout-swing:11.0")
    // implementation("com.github.goxr3plus:java-stream-player:10.0.2")
    implementation("com.googlecode.soundlibs:jlayer:1.0.1.4")

    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    testImplementation("org.projectlombok:lombok:1.18.24")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.24")
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2022.2.1")
    type.set("IC") // Target IDE Platform
    downloadSources.set(true)

    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    patchPluginXml {
        sinceBuild.set("213")
        untilBuild.set("222.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
