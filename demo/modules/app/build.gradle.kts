plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jooq.jooq-codegen-gradle") version "3.21.6"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":skill-openai"))
    implementation(project(":validation-openai"))

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    // Spring Boot 4.x — specific starters (modular design)
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-jackson2")
    runtimeOnly("org.springframework.boot:spring-boot-properties-migrator")

    // Database
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-flyway")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    runtimeOnly("org.postgresql:postgresql")
    jooqCodegen("org.postgresql:postgresql")

    // jOOQ codegen
    jooqCodegen("org.jooq:jooq-codegen:${property("jooqVersion")}")
    jooqCodegen("org.jooq:jooq-meta-extensions:${property("jooqVersion")}")

    // CLI
    implementation("info.picocli:picocli-spring-boot-starter:4.7.7")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:testcontainers:${property("testcontainersVersion")}")
    testImplementation("org.testcontainers:testcontainers-postgresql:${property("testcontainersVersion")}")
    testImplementation("org.testcontainers:testcontainers-junit-jupiter:${property("testcontainersVersion")}")
    testImplementation("com.tngtech.archunit:archunit-junit5:${property("archunitVersion")}")
}

jooq {
    configuration {
        jdbc {
            driver = "org.postgresql.Driver"
            url = "jdbc:postgresql://localhost:5432/pnyx"
            user = "pnyx"
            password = "pnyx_dev"
        }
        generator {
            name = "org.jooq.codegen.JavaGenerator"
            database {
                name = "org.jooq.meta.postgres.PostgresDatabase"
                inputSchema = "public"
                excludes = "flyway_schema_history"
            }
            target {
                packageName = "dev.pnyx.infrastructure.eventstore.jooq"
                directory = "src/generated/java"
            }
            generate {
                isRecords = true
                isPojos = false
                isFluentSetters = true
            }
        }
    }
}

sourceSets {
    main {
        java {
            srcDir("src/generated/java")
        }
    }
}

// Exclude generated jOOQ codegen output from Checkstyle
tasks.named<Checkstyle>("checkstyleMain") {
    setSource(fileTree("src/main/java"))
}

// Run the app from the project root so relative data paths (./data/prompts,
// ./data/public) resolve correctly. Per AGENTS.md, the demo corpus lives at
// the repository root under data/.
tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    workingDir = rootProject.projectDir
}
