package dev.pnyx.infrastructure.skill;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@SuppressWarnings("PMD.LooseCoupling")
class SkillOpenAiArchitectureTest {

    private final JavaClasses classes = new ClassFileImporter()
        .withImportOption(new ImportOption.DoNotIncludeTests())
        .importPackages("dev.pnyx.infrastructure.skill");

    @Test
    void pluginDoesNotDependOnAppRuntimePackages() {
        noClasses().that().resideInAPackage("dev.pnyx.infrastructure.skill..")
            .should().dependOnClassesThat().resideInAnyPackage(
                "dev.pnyx.endpoint..",
                "dev.pnyx.service..",
                "dev.pnyx.infrastructure.config..",
                "dev.pnyx.infrastructure.publicstore..",
                "dev.pnyx.infrastructure.eventstore.."
            )
            .check(classes);
    }
}