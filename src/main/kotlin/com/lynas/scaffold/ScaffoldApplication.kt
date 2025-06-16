package com.lynas.scaffold

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@ConfigurationPropertiesScan
@EnableTransactionManagement
@SpringBootApplication
class ScaffoldApplication

fun main(args: Array<String>) {
	runApplication<ScaffoldApplication>(*args)
}
