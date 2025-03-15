plugins {
	alias(libs.plugins.kotlin.jvm)
	alias(libs.plugins.kotlin.spring)
	alias(libs.plugins.kotlin.serialization)
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
}

group = "dev.mcarr"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation(libs.exposed.core)
	implementation(libs.exposed.jdbc)
	implementation(libs.exposed.spring.boot.starter)
	implementation("com.h2database:h2")
	implementation(libs.db.connector.mariadb)
	implementation(libs.db.connector.mysql)
	implementation(libs.db.connector.postgres)
	implementation(libs.db.connector.sqlite)
	implementation(libs.kotlinx.serialization.json)
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
	jvmToolchain(21)
}

tasks.withType<Test> {
	useJUnitPlatform()
}

