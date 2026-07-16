package dev.pnyx;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@SuppressWarnings("PMD.LooseCoupling")
class ArchitectureTest {

    private static final String ENDPOINT_PKG = "dev.pnyx.endpoint..";
    private static final String SERVICE_PKG = "dev.pnyx.service..";
    private static final String INFRASTRUCTURE_PKG = "dev.pnyx.infrastructure..";
    private static final String CORE_PKG = "dev.pnyx.core..";
    private static final String CORE_API_PKG = "dev.pnyx.core.api..";
    private static final String SPI_PKG = "dev.pnyx.core.spi..";

    private final JavaClasses classes = new ClassFileImporter()
        .withImportOption(new ImportOption.DoNotIncludeTests())
        .importPackages("dev.pnyx");

    @Test
    void coreDoesNotDependOnEndpoint() {
        noClasses().that().resideInAPackage(CORE_PKG)
            .should().dependOnClassesThat().resideInAPackage(ENDPOINT_PKG)
            .allowEmptyShould(true).check(classes);
    }

    @Test
    void coreDoesNotDependOnInfrastructure() {
        noClasses().that().resideInAPackage(CORE_PKG)
            .should().dependOnClassesThat().resideInAPackage(INFRASTRUCTURE_PKG)
            .allowEmptyShould(true).check(classes);
    }

    @Test
    void infrastructureDoesNotDependOnEndpoint() {
        noClasses().that().resideInAPackage(INFRASTRUCTURE_PKG)
            .should().dependOnClassesThat().resideInAPackage(ENDPOINT_PKG)
            .allowEmptyShould(true).check(classes);
    }

    @Test
    void endpointDoesNotDependOnInfrastructure() {
        noClasses().that().resideInAPackage(ENDPOINT_PKG)
            .should().dependOnClassesThat().resideInAPackage(INFRASTRUCTURE_PKG)
            .allowEmptyShould(true).check(classes);
    }

    @Test
    void endpointDoesNotCallSpiDirectly() {
        noClasses().that().resideInAPackage(ENDPOINT_PKG)
            .should().dependOnClassesThat().resideInAPackage(SPI_PKG)
            .allowEmptyShould(true).check(classes);
    }

    @Test
    void servicesDoNotDependOnInfrastructureAdapters() {
        noClasses().that().resideInAPackage(SERVICE_PKG)
            .should().dependOnClassesThat().resideInAPackage(INFRASTRUCTURE_PKG)
            .allowEmptyShould(true).check(classes);
    }

    @Test
    void infrastructureDoesNotDependOnUseCaseApis() {
        noClasses().that().resideInAPackage(INFRASTRUCTURE_PKG)
            .should().dependOnClassesThat().resideInAPackage(CORE_API_PKG)
            .allowEmptyShould(true).check(classes);
    }

    @Test
    void infrastructureDoesNotDependOnEndpoints() {
        noClasses().that().resideInAPackage(INFRASTRUCTURE_PKG)
            .should().dependOnClassesThat().resideInAPackage(ENDPOINT_PKG)
            .allowEmptyShould(true).check(classes);
    }
}