plugins {
    `java-library`
}

dependencies {
    // Zero framework dependencies — core is pure Java.
    // Only test deps:
    testImplementation(platform("org.junit:junit-bom:${property("junitVersion")}"))
    testImplementation("com.tngtech.archunit:archunit-junit5:${property("archunitVersion")}")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.assertj:assertj-core:3.27.7")
}
