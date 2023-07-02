package com.example.cep_app_android.ui.searchCep

import Endereco
import ViaCepRepository
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cep_app_android.ui.cepResult.CepResultActivity
import kotlinx.coroutines.launch

class SearchCepViewModel : ViewModel() {
    private val viaCepRepository = ViaCepRepository()
    private lateinit var sharedPreferences: SharedPreferences

    private val _endereco = MutableLiveData<Endereco?>()
    val endereco: LiveData<Endereco?> get() = _endereco

    private val _erro = MutableLiveData<String>()
    val erro: LiveData<String> get() = _erro

    fun consultarCep(cep: String, context: Context) {
        sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

        viewModelScope.launch {
            try {
                val response = viaCepRepository.consultarCep(cep)
                if (response.isSuccess) {
                    val endereco = response.getOrNull()
                    if (endereco != null) {
                        _endereco.value = endereco

                        // Limpar os dados antigos
                        val editor = sharedPreferences.edit()
                        editor.clear()
                        editor.apply()

                        // Salvar os novos dados
                        val editorNew = sharedPreferences.edit()
                        editorNew.putString("endereco", endereco.toString())
                        editorNew.putString("cep", endereco.cep)
                        editorNew.putString("logradouro", endereco.logradouro)
                        editorNew.putString("bairro", endereco.bairro)
                        editorNew.putString("localidade", endereco.localidade)
                        editorNew.putString("uf", endereco.uf)
                        editorNew.putString("ibge", endereco.ibge)
                        editorNew.putString("ddd", endereco.ddd)
                        editorNew.apply()

                        val intent = Intent(context, CepResultActivity::class.java).apply {
                            putExtra("logradouro", endereco.logradouro)
                            putExtra("bairro", endereco.bairro)
                            putExtra("localidade", endereco.localidade)
                            putExtra("uf", endereco.uf)
                            putExtra("ibge", endereco.ibge)
                            putExtra("ddd", endereco.ddd)
                        }
                        context.startActivity(intent)
                    } else {
                        showErrorAlert(context, "Endereço não encontrado")
                    }
                } else {
                    showErrorAlert(context, "Erro ao consultar o CEP")
                }
            } catch (e: Exception) {
                showErrorAlert(context, "Erro de comunicação com o servidor")
            }
        }
    }

    private fun showErrorAlert(context: Context, message: String) {
        val alertDialog = AlertDialog.Builder(context)
            .setTitle("Erro")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .create()

        alertDialog.show()
    }
}
