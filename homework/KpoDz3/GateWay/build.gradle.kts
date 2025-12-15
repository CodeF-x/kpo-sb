plugins {
	java
	id("org.springframework.boot") version "3.5.8"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "KPODZ3"
version = "0.0.1-SNAPSHOT"
description = "kpodz3"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("io.swagger.core.v3:swagger-annotations:2.2.41")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
	
}

tasks.withType<Test> {
	useJUnitPlatform()
}
