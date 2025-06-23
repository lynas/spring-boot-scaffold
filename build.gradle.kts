plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.0"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.asciidoctor.jvm.convert") version "3.3.2"
	kotlin("plugin.jpa") version "1.9.25"
}

group = "com.lynas"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

extra["snippetsDir"] = file("build/generated-snippets")
val mockkVersion = "1.14.2"
val archunitJunit5Version = "1.4.1"
val resilience4jVersion = "2.3.0"
val wireMockVersion = "3.10.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-amqp")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-database-postgresql")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.github.microutils:kotlin-logging:3.0.5")

	implementation("io.github.resilience4j:resilience4j-spring-boot3:${resilience4jVersion}")
	implementation("org.springframework.boot:spring-boot-starter-aop")

//	implementation("io.github.resilience4j:resilience4j-annotations:$resilience4jVersion")
//	implementation("io.github.resilience4j:resilience4j-retry:$resilience4jVersion")
//	implementation("io.github.resilience4j:resilience4j-circuitbreaker:$resilience4jVersion")

	implementation("org.apache.httpcomponents.client5:httpclient5:5.5")

	developmentOnly("org.springframework.boot:spring-boot-docker-compose")

	runtimeOnly("org.postgresql:postgresql")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.amqp:spring-rabbit-test")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")
	testImplementation("org.testcontainers:rabbitmq")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	testImplementation("io.mockk:mockk:$mockkVersion")
	testImplementation("com.tngtech.archunit:archunit-junit5:$archunitJunit5Version")
	testImplementation("io.github.resilience4j:resilience4j-test:$resilience4jVersion")
	testImplementation("org.wiremock.integrations:wiremock-spring-boot:$wireMockVersion")


}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
	outputs.dir(project.extra["snippetsDir"]!!)
}

tasks.asciidoctor {
	inputs.dir(project.extra["snippetsDir"]!!)
	dependsOn(tasks.test)
}
