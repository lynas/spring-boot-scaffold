package com.lynas.testing.process

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TestingProcessApplication

fun main(args: Array<String>) {
	runApplication<TestingProcessApplication>(*args)
}
