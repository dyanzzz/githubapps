package com.accenture.githubapps.api

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okio.GzipSource
import okio.buffer
import java.io.IOException

class GzipInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request())
        return unzip(response)
    }

    @Throws(IOException::class)
    private fun unzip(response: Response): Response {
        if (response.body == null) {
            return response
        }

        //check if we have gzip response
        val contentType = response.headers["Content-Type"]
        val mediaType = response.body!!.contentType()

        //this is used to decompress gzipped responses
        return if (contentType != null && contentType == "application/gzip") {
            val contentLength = response.body!!.contentLength()
            val responseBody = GzipSource(response.body!!.source())
            val strippedHeaders = response.headers.newBuilder().build()
            val jsonResponse = responseBody.buffer().readUtf8()
            val body = ResponseBody.create(mediaType, jsonResponse)
            response.newBuilder()
                .body(body)
                .headers(strippedHeaders)
                .build()
        } else {
            response
        }
    }
}