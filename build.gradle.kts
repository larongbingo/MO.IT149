import java.io.FileInputStream
import java.util.Properties

val envProperties = Properties()
val envFile: File? = rootProject.file("env.properties")
if (envFile != null && envFile.exists()) {
    envProperties.load(FileInputStream(envFile))
}

plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"
    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.google.cloud.tools.jib") version "3.5.2"
}

group = "org.moit149"
version = "0.0.1"
description = "connectly-moit 149"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

repositories {
    mavenCentral()
}

jib {
    to {
        image = "connectly-moit149"
    }
    container {
        ports = listOf("8080")
    }
    dockerClient {
        environment = mapOf(
            "DB_CONNECTION_STRING" to envProperties.getProperty("DB_CONNECTION_STRING"),
            "OAUTH_CLIENT_ID" to envProperties.getProperty("OAUTH_CLIENT_ID"),
            )
    }
}

dependencies {
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-micrometer-metrics")
    implementation("org.springframework.boot:spring-boot-starter-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("tools.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-restclient")
    //runtimeOnly("io.micrometer:micrometer-registry-new-relic")
    testImplementation("org.springframework.boot:spring-boot-starter-micrometer-metrics-test")
    testImplementation("org.springframework.boot:spring-boot-starter-mongodb-test")
    testImplementation("org.springframework.boot:spring-boot-starter-security-oauth2-client-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
