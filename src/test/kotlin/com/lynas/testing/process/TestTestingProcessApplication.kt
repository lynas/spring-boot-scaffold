package com.lynas.testing.process

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<ScaffoldApplication>().with(TestcontainersConfiguration::class).run(*args)
}
