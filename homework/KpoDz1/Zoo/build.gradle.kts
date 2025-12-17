plugins {
	java
	application
	id("io.spring.dependency-management") version "1.1.7"
}

group = "KPODZ1"
version = "0.0.1-SNAPSHOT"
description = "ZooTopia 3"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework:spring-context:6.1.1")
	testImplementation ("org.junit.jupiter:junit-jupiter:5.10.0")
	testImplementation ("org.mockito:mockito-junit-jupiter:5.7.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
	testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

application {
    mainClass.set("KPODZ1.Zoo.ZooApplication") 
}

tasks.withType<JavaExec> {
    standardInput = System.`in`
}