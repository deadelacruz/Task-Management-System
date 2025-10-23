plugins {
	java
	id("org.springframework.boot") version "4.0.0-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.github.node-gradle.node") version "3.5.1"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	// Spring Boot Starters
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	
	// Database
	implementation("com.h2database:h2")
	runtimeOnly("com.mysql:mysql-connector-j:8.0.33")
	runtimeOnly("org.postgresql:postgresql:42.6.0")
	
	// JWT Security
	implementation("io.jsonwebtoken:jjwt-api:0.12.3")
	implementation("io.jsonwebtoken:jjwt-impl:0.12.3")
	implementation("io.jsonwebtoken:jjwt-jackson:0.12.3")
	
	// JSON Processing
	implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.2")
	
	// Validation
	implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
	
	// Monitoring
	implementation("io.micrometer:micrometer-registry-prometheus:1.11.0")
	
	// Development Tools
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	
	// Testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.testcontainers:junit-jupiter:1.18.3")
	testImplementation("org.testcontainers:mysql:1.18.3")
	testImplementation("org.testcontainers:postgresql:1.18.3")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

// Node.js configuration
node {
	version = "18.17.0"
	npmVersion = "9.6.7"
	download = true
	nodeProjectDir = file("frontend")
}

// Task to install npm dependencies
tasks.register<com.github.gradle.node.npm.task.NpmTask>("installNpmDependencies") {
	group = "build"
	description = "Install npm dependencies"
	workingDir = file("frontend")
	args.set(listOf("install"))
}

// Task to build React frontend
tasks.register<com.github.gradle.node.npm.task.NpmTask>("buildReact") {
	dependsOn("installNpmDependencies")
	group = "build"
	description = "Build React frontend"
	workingDir = file("frontend")
	args.set(listOf("run", "build"))
	
	// Ensure the build directory exists
	doFirst {
		file("frontend/build").mkdirs()
	}
}

// Task to run React development server
tasks.register<com.github.gradle.node.npm.task.NpmTask>("startReact") {
	dependsOn("installNpmDependencies")
	group = "application"
	description = "Start React development server"
	workingDir = file("frontend")
	args.set(listOf("start"))
}

// Task to run React tests
tasks.register<com.github.gradle.node.npm.task.NpmTask>("testReact") {
	dependsOn("installNpmDependencies")
	group = "verification"
	description = "Run React tests"
	workingDir = file("frontend")
	args.set(listOf("test", "--coverage", "--watchAll=false"))
}

// Task to lint React code
tasks.register<com.github.gradle.node.npm.task.NpmTask>("lintReact") {
	dependsOn("installNpmDependencies")
	group = "verification"
	description = "Lint React code"
	workingDir = file("frontend")
	args.set(listOf("run", "lint"))
}

// Task to copy React build to Spring Boot static resources
tasks.register<Copy>("copyReactBuild") {
	group = "build"
	description = "Copy React build to Spring Boot static resources"
	
	from("frontend/build")
	into("src/main/resources/static")
	
	// Preserve directory structure
	include("**/*")
}

// Make build task depend on React build
tasks.named("build") {
	dependsOn("copyReactBuild")
}

// Clean task to remove React build
tasks.register<Delete>("cleanReact") {
	group = "clean"
	description = "Clean React build files"
	delete("frontend/build")
	delete("frontend/node_modules")
	delete("src/main/resources/static")
	delete("build/resources/main/static")
}

tasks.named("clean") {
	dependsOn("cleanReact")
}

// Task to run both Spring Boot and React in development
tasks.register<Exec>("runDev") {
	group = "application"
	description = "Run both Spring Boot and React in development mode"
	dependsOn("bootRun")
}

// Task to check if Node.js is available
tasks.register<Exec>("checkNode") {
	group = "verification"
	description = "Check if Node.js is available"
	commandLine("node", "--version")
}

// Ensure React build before Spring Boot build
tasks.named("processResources") {
	dependsOn("copyReactBuild")
}

// Development configuration
tasks.named("bootRun") {
	dependsOn("copyReactBuild")
}
