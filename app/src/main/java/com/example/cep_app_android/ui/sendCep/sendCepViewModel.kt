package com.example.cep_app_android.ui.sendCep

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class SendCepViewModel : ViewModel() {
    private val webhookUrl = "https://discord.com/api/webhooks/1095399212310593597/wI4rSHU4bI3jGTz7XoV3LCKJ7licceu4_bz2G3yJt8bN9aHSIdE6ZSdF6UTDPjW8fEiP"

    fun sendLogsToDiscord(
        cep: String,
        logradouro: String,
        bairro: String,
        localidade: String,
        uf: String,
        ibge: String,
        ddd: String?,
        onLogsSent: () -> Unit,
        onLogsSendFailed: (String) -> Unit
    ) {
        val logMessage = """
            Log
            ConsultaCep: $cep, Modelocelular: XXXX
            RetornoCep:
            - Logradouro: $logradouro
            - Bairro: $bairro
            - Localidade: $localidade
            - UF: $uf
            - IBGE: $ibge
            - DDD: $ddd
        """.trimIndent()

        val bodyJson = Gson().toJson(LogMessage(logMessage))
        val client = OkHttpClient()
        val requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyJson)
        val request = Request.Builder()
            .url(webhookUrl)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onLogsSendFailed("Failed to send logs to Discord: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    onLogsSent()
                } else {
                    onLogsSendFailed("Failed to send logs to Discord: ${response.code()}")
                }
            }
        })
    }

    inner class LogMessage(val content: String)
}