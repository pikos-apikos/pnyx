package dev.pnyx.core;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@SuppressWarnings("PMD.LooseCoupling")
class ArchitectureTest {

    private static final String API_PKG = "dev.pnyx.core.api..";
    private static final String SPI_PKG = "dev.pnyx.core.spi..";
    private static final String DOMAIN_PKG = "dev.pnyx.core.domain..";
    private static final String CORE_PKG = "dev.pnyx.core";

    private final JavaClasses classes = new ClassFileImporter()
        .withImportOption(new ImportOption.DoNotIncludeTests())
        .importPackages(CORE_PKG);

    @Test
    void domainModelsDoNotDependOnApi() {
        noClasses().that().resideInAPackage(DOMAIN_PKG)
            .should().dependOnClassesThat().resideInAPackage(API_PKG)
            .allowEmptyShould(true).check(classes);
    }

    @Test
    void domainModelsDoNotDependOnSpi() {
        noClasses().that().resideInAPackage(DOMAIN_PKG)
            .should().dependOnClassesThat().resideInAPackage(SPI_PKG)
            .allowEmptyShould(true).check(classes);
    }

    @Test
    void apiClassesAreOnlyInterfaces() {
        var rule = noClasses().that()
            .resideInAPackage(API_PKG)
            .and().haveNameNotMatching(".*\\$.*")
            .should().notBeInterfaces();
        rule.allowEmptyShould(true).check(classes);
    }

    @Test
    void spiClassesAreOnlyInterfaces() {
        var rule = noClasses().that()
            .resideInAPackage(SPI_PKG)
            .and().haveNameNotMatching(".*\\$.*")
            .should().notBeInterfaces();
        rule.allowEmptyShould(true).check(classes);
    }

    @Test
    void domainHasNoFrameworkDependencies() {
        noClasses().that().resideInAPackage(DOMAIN_PKG)
            .should().dependOnClassesThat()
            .haveNameMatching("jakarta\\..*")
            .orShould().dependOnClassesThat()
            .haveNameMatching("javax\\..*")
            .orShould().dependOnClassesThat()
            .haveNameMatching("org\\.springframework..*")
            .allowEmptyShould(true).check(classes);
    }

    @Test
    void apiDoesNotDependOnSpi() {
        noClasses().that().resideInAPackage(API_PKG)
            .should().dependOnClassesThat().resideInAPackage(SPI_PKG)
            .allowEmptyShould(true).check(classes);
    }

    @Test
    void spiDoesNotDependOnApi() {
        noClasses().that().resideInAPackage(SPI_PKG)
            .should().dependOnClassesThat().resideInAPackage(API_PKG)
            .allowEmptyShould(true).check(classes);
    }
}
