package com.lynas.scaffold

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ScaffoldApplication

fun main(args: Array<String>) {
	runApplication<ScaffoldApplication>(*args)
}
