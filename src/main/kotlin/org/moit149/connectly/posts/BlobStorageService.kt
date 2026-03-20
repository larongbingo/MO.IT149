package org.moit149.connectly.posts

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.PutObjectRequest
import aws.smithy.kotlin.runtime.content.ByteStream
import aws.smithy.kotlin.runtime.net.url.Url
import org.springframework.stereotype.Service

@Service
class S3BlobStorageService : BlobStorage {
    private val bucketName = System.getenv("S3_BUCKET_NAME")

    private val publicUrl = System.getenv("S3_PUBLIC_URL")
    private val accountId = System.getenv("S3_ACCOUNT_ID")

    private val credentials = StaticCredentialsProvider {
        accessKeyId = System.getenv("S3_ACCESS_KEY_ID")
        secretAccessKey = System.getenv("S3_SECRET_ACCESS_KEY")
    }

    private val s3Client = S3Client {
        region = "auto"
        endpointUrl = Url.parse("https://$accountId.r2.cloudflarestorage.com")
        credentialsProvider = credentials
        forcePathStyle = true
   }

    override suspend fun upload(file: ByteArray, fileName: String): String? {
        val request = PutObjectRequest {
            bucket = bucketName
            key = fileName
            body = ByteStream.fromBytes(file)
        }

        val res = s3Client.putObject(request)

        return "$publicUrl/$fileName"
    }
}

interface BlobStorage {
    suspend fun upload(file: ByteArray, fileName: String): String?
}