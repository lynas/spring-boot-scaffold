package com.lynas.testing.process

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import kotlin.reflect.KCallable
import kotlin.reflect.full.findAnnotation

val objectMapper = jacksonObjectMapper()

inline fun <reified T> MockHttpServletResponse.toKObject(): T {
    return objectMapper.readValue(this.contentAsString, T::class.java)
}

fun KCallable<*>.getRequestUrl(): String {
    this.findAnnotation<GetMapping>()?.let { return it.value.first() }
    this.findAnnotation<PostMapping>()?.let { return it.value.first() }
    this.findAnnotation<PutMapping>()?.let { return it.value.first() }
    this.findAnnotation<DeleteMapping>()?.let { return it.value.first() }
    this.findAnnotation<RequestMapping>()?.let { return it.value.first() }
    throw RuntimeException("Method is not a valid controller method")
}