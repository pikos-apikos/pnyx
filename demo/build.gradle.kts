plugins {
    id("org.springframework.boot") version "4.1.0" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
    id("com.github.spotbugs") version "6.5.9" apply false
}

allprojects {
    group = "dev.pnyx"
    version = "0.1.0-SNAPSHOT"

    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/milestone") }
    }
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "checkstyle")

    configure<JavaPluginExtension> {
        toolchain {
            languageVersion = JavaLanguageVersion.of(project.property("javaVersion").toString())
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    configure<CheckstyleExtension> {
        toolVersion = project.property("checkstyleVersion").toString()
        configFile = rootProject.file("config/checkstyle/checkstyle.xml")
        isIgnoreFailures = true  // warnings only — existing code has gaps
        maxWarnings = 0          // but warn on every violation
    }

    // Exclude generated sources (jOOQ codegen output) from Checkstyle
    tasks.withType<Checkstyle> {
        exclude("**/generated/**")
    }

    // PMD, SpotBugs, JaCoCo — see gradle/codestyle.gradle
    apply(from = "${rootDir}/gradle/codestyle.gradle")
}

/**
 * Verifies STATE.md and JOURNAL.md are present and STATE.md has a recent `updated:` timestamp.
 * Fails if STATE.md is missing or if the `updated:` date is more than 7 days old.
 */
tasks.register("verifyContextFreshness") {
    description = "Verify STATE.md and JOURNAL.md are present and current"
    
    doLast {
        val stateFile = file("STATE.md")
        val journalFile = file("JOURNAL.md")
        
        if (!stateFile.exists()) {
            throw GradleException("STATE.md not found at ${stateFile.absolutePath}")
        }
        if (!journalFile.exists()) {
            throw GradleException("JOURNAL.md not found at ${journalFile.absolutePath}")
        }
        
        val stateContent = stateFile.readText()
        val updatedLine = stateContent.lines().find { it.trimStart().startsWith("> **Updated:**") }
            ?: throw GradleException("STATE.md missing '> **Updated:**' line")
        
        val dateStr = updatedLine.replaceFirst(".*> \\*\\*Updated:\\*\\*\\s*".toRegex(), "").trim()
        try {
            val updated = java.time.LocalDate.parse(dateStr)
            val now = java.time.LocalDate.now()
            val daysSince = java.time.temporal.ChronoUnit.DAYS.between(updated, now)
            if (daysSince > 7) {
                throw GradleException("STATE.md updated: $dateStr is $daysSince days old (max 7)")
            }
        } catch (e: java.time.format.DateTimeParseException) {
            throw GradleException("STATE.md has unparseable date: '$dateStr'")
        }
    }
}
