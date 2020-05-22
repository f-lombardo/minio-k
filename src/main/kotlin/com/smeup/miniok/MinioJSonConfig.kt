package com.smeup.miniok

data class MinioJSonConfig(val hosts: Map<String, MinioHostConfiguration>, val version: String)

data class MinioHostConfiguration(
    val url: String,
    val accessKey: String,
    val secretKey: String,
    val lookup: String? = null,
    val api: String? = null
)

