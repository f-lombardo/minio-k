package com.smeup.miniok


import com.google.gson.Gson

import io.minio.MinioClient
import java.io.File


fun hostConfigurationFromEnv(): HostConfiguration? {
    val endPoint = System.getenv("endpoint") ?: return null
    val accessKey = System.getenv("accessKey") ?: return null
    val secretKey = System.getenv("secretKey") ?: return null
    return HostConfiguration(endPoint, accessKey, secretKey)
}

private fun String.hostConfigurationFromFile(): HostConfiguration? {
    val fileName = System.getProperty("user.home") + "/mc/config.json"
    val json = File(fileName).readText(Charsets.UTF_8)
    return Gson()
            .fromJson(json, MinioJSonConfig::class.java)
            ?.hosts
            ?.get(this)
            ?.toHostConfiguration()
}

data class MinioCommandParameters(val configurationName: String?, val bucketName: String, val objectName: String, val localFileName: String)

private fun findCommandLineParameters(args: Array<String>): MinioCommandParameters? {
    if (args.size < 3) {
        return null
    }
    val reversedArgs = args.asList().reversed()
    return MinioCommandParameters(if (reversedArgs.size > 3) reversedArgs[3] else null ,
                                 reversedArgs[2],
                                 reversedArgs[1],
                                 reversedArgs[0])
}

fun usage() =
    "This program needs these parameters: [configurationName] bucketName objectName localFileName"

/**
 * Uploads a file into an S3 compatible bucket
 * The host configuration can be provided in two ways:
 * 1. Using the environment variables endpoint, accessKey, secretKey
 * 2. Through the mc/config.json (it's the same that mc uses) and passing a configuration name as first parameter to the program
 *
 * The command line parameters are these:
 * [configurationName] bucketName objectName localFileName
 */
object FileUploader {
    @JvmStatic
    fun main(args: Array<String>) {
        val minioCommandParameters =
            findCommandLineParameters(args) ?: throw RuntimeException(usage())

        val hostConfiguration =
            hostConfigurationFromEnv()
            ?: minioCommandParameters.configurationName?.hostConfigurationFromFile()
            ?: throw RuntimeException("No configuration can be found")

        println("Connecting to ${hostConfiguration.url}")

        val minioClient = MinioClient(hostConfiguration.url, hostConfiguration.accessKey, hostConfiguration.secretKey)

        println("Creating $minioCommandParameters")

        if (minioClient.bucketExists(minioCommandParameters.bucketName)) {
            println("Bucket ${minioCommandParameters.bucketName} already exists.")
        } else {
            minioClient.makeBucket(minioCommandParameters.bucketName)
            println("Bucket ${minioCommandParameters.bucketName} created.")
        }

        minioClient.putObject(
            minioCommandParameters.bucketName,
            minioCommandParameters.objectName,
            minioCommandParameters.localFileName,
            null)

        println("OK")
    }
}
