package com.smeup.miniok

import io.minio.MinioClient
import io.minio.errors.MinioException
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException

object FileUploader {
    @JvmStatic
    fun main(args: Array<String>) {
        try {
            val minioClient =
                MinioClient("http://localhost:9000", "minioadmin", "minioadmin")

            val isExist = minioClient.bucketExists("images")
            if (isExist) {
                println("Bucket already exists.")
            } else {
                minioClient.makeBucket("images")
            }

            minioClient.putObject("images", "kotlin/kotlin-from-scratch.jpg", "/Users/francol/gdrive/SINCRO/KotlinFromScratch/kotlin-from-scratch.jpg", null)
            println("OK")
        } catch (e: MinioException) {
            println("Error occurred: $e")
        }
    }
}
