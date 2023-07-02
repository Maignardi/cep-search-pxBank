package com.example.cep_app_android.ui.cepResult

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cep_app_android.databinding.ActivityCepResultBinding

class CepResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCepResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCepResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val logradouro = intent.getStringExtra("logradouro")
        val bairro = intent.getStringExtra("bairro")
        val localidade = intent.getStringExtra("localidade")
        val uf = intent.getStringExtra("uf")
        val ibge = intent.getStringExtra("ibge")
        val ddd = intent.getStringExtra("ddd")

        binding.textViewLogradouro.text = "Logradouro: $logradouro"
        binding.textViewBairro.text = "Bairro: $bairro"
        binding.textViewLocalidade.text = "Localidade: $localidade"
        binding.textViewUF.text = "UF: $uf"
        binding.textViewIBGE.text = "IBGE: $ibge"
        binding.textViewDDD.text = "DDD: $ddd"

        binding.buttonNovaPesquisa.setOnClickListener {
            finish()
        }
    }
}
