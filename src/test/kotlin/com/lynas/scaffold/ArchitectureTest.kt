package com.lynas.scaffold

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import org.junit.jupiter.api.Test


class ArchitectureTest {

    @Test
    fun `service class should be accessible from another service or controller class`(){
        val myRule: ArchRule = classes()
            .that().resideInAPackage("..service..")
            .should().onlyBeAccessed()
            .byAnyPackage("..controller..", "..service..")
        val importedClasses = ClassFileImporter().importPackages("com.lynas.scaffold.service")
        myRule.check(importedClasses);
    }
}