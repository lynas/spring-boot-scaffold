package com.lynas.scaffold.service.ext

interface ExtRestApiService {
    fun <T> get(url: String, queryParams: Map<String, String>, responseType: Class<T>): T
}