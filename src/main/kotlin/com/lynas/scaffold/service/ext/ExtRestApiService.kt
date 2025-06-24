package com.lynas.scaffold.service.ext

import java.util.concurrent.CompletableFuture

interface ExtRestApiService {
    fun <T> get(url: String, queryParams: Map<String, String>, responseType: Class<T>): CompletableFuture<T>
}