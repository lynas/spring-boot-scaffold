package com.lynas.scaffold

import com.tngtech.archunit.core.domain.JavaClass
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchCondition
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.ConditionEvents
import com.tngtech.archunit.lang.SimpleConditionEvent
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes

/**
 * Analyze main code
 */
@AnalyzeClasses(packages = ["com.lynas.scaffold"],
    importOptions = [ImportOption.DoNotIncludeTests::class])
class LayerRulesTest {
    /**
     * 1)  Service classes may access *one* repository class, and the name must match.
     */
    @ArchTest
    val serviceUsesOnlyItsOwnRepository: ArchRule =
        classes().that().haveSimpleNameEndingWith("Service")
            .should(usesOnlyOwnRepository())


    private fun usesOnlyOwnRepository(): ArchCondition<JavaClass> =
        object : ArchCondition<JavaClass>("only depend on its namesake Repository") {

            override fun check(serviceClass: JavaClass, events: ConditionEvents) {
                val expectedRepoName = serviceClass.simpleName.replace("Service", "Repository")
                val accesses = serviceClass
                    .accessesFromSelf  // field + method + constructor calls
                    .map { it.target.owner.simpleName }
                    .filter { it.endsWith("Repository") }
                    .toSet()

                // ✔ acceptable: zero (service is mocked in tests) or exactly its own repo
                val ok = accesses.isEmpty() || accesses == setOf(expectedRepoName)

                if (!ok) {
                    val msg =
                        "${serviceClass.simpleName} touches ${accesses.joinToString()} " +
                                "but should reference only $expectedRepoName"
                    events.add(SimpleConditionEvent.violated(serviceClass, msg))
                }
            }
        }
}