package helpers

import BASE_URL
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import strikt.api.expectCatching
import strikt.assertions.isSuccess


fun buildRequestURL(method: String, resource: String = "", queryParam: Map<String, String>? = null): String {
    var finalUrl = BASE_URL + method
    if (resource != "") {
        finalUrl += "/$resource"
    }
    if (queryParam != null) {
        var paramStr = "?"
        queryParam.forEach { (key, value) ->
            paramStr += "$key=$value&"
        }
        finalUrl += paramStr.dropLast(1)
    }
    return finalUrl
}

fun <T> buildRequestBody(bodyObj: T): String =
    GsonBuilder().create().toJson(bodyObj)

fun sendPostRequest(url: String, body: String): Response {
    val json = "application/json; charset=utf-8".toMediaType()
    val client = OkHttpClient()
    val reqBody = body.toRequestBody(json)
    val req = Request.Builder()
        .url(url)
        .post(reqBody)
        .header("Content-Type", "application/json")
        .build()
    LOGGER.info {"Send POST request\n url: ${req.url}\n headers: ${req.headers.toString().trim()}\n body: ${body}"}
    return client.newCall(req).execute()
}

fun sendGetRequest(url: String): Response {
    val client = OkHttpClient()
    val req = Request.Builder()
        .url(url)
        .get()
        .header("Content-Type", "application/json")
        .build()
    LOGGER.info {"Send GET request\n url: ${req.url}\n headers: ${req.headers.toString().trim()}"}
    return client.newCall(req).execute()
}

fun sendPutRequest(url: String, body: String): Response {
    val json = "application/json; charset=utf-8".toMediaType()
    val client = OkHttpClient()
    val reqBody = body.toRequestBody(json)
    val req = Request.Builder()
        .url(url)
        .put(reqBody)
        .header("Content-Type", "application/json")
        .build()
    LOGGER.info {"Send PUT request\n url: ${req.url}\n headers: ${req.headers.toString().trim()}\n body: ${body}"}
    return client.newCall(req).execute()
}

fun sendPatchRequest(url: String, body: String): Response {
    val json = "application/json; charset=utf-8".toMediaType()
    val client = OkHttpClient()
    val reqBody = body.toRequestBody(json)
    val req = Request.Builder()
        .url(url)
        .patch(reqBody)
        .header("Content-Type", "application/json")
        .build()
    LOGGER.info {"Send PATCH request\n url: ${req.url}\n headers: ${req.headers.toString().trim()}\n body: ${body}"}
    return client.newCall(req).execute()
}

fun sendDeleteRequest(url: String): Response {
    val client = OkHttpClient()
    val req = Request.Builder()
        .url(url)
        .delete()
        .header("Content-Type", "application/json")
        .build()
    LOGGER.info {"Send DELETE request\n url: ${req.url}\n headers: ${req.headers.toString().trim()}"}
    return client.newCall(req).execute()
}

inline fun <reified T: Any> parseResponseBody(response: Response): T =
    expectCatching {
        jacksonObjectMapper().readValue(response.body.string(), T::class.java)
    }.describedAs("response body is invalid").isSuccess().subject
