plugins {
	java
	application
	id("io.spring.dependency-management") version "1.1.7"
}

group = "KPODZ2"
version = "0.0.1-SNAPSHOT"
description = "Bank app"

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
	implementation("org.projectlombok:lombok:1.18.42")
	compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    
    testCompileOnly("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
}

application {
    mainClass.set("KPODZ2.Bank.BankApplication") 
}

tasks.withType<JavaExec> {
    standardInput = System.`in`
}