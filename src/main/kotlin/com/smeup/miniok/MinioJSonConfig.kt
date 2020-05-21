package com.smeup.miniok

data class HostConfiguration(val url: String, val accessKey: String, val secretKey: String)

data class MinioJSonConfig(val hosts: Map<String, MinioHost>, val version: String)

data class MinioHost(
    val url: String,
    val accessKey: String,
    val secretKey: String,
    val lookup: String,
    val api: String
) {
    fun toHostConfiguration() = HostConfiguration(url, accessKey, secretKey)
}

