package com.example.cep_app_android.ui.sendCep

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cep_app_android.databinding.FragmentSendCepBinding

class SendCepFragment : Fragment() {

    private var _binding: FragmentSendCepBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SendCepViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSendCepBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SendCepViewModel::class.java)

        val sharedPreferences = requireContext().getSharedPreferences("MySharedPreferences",
            Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val cep = sharedPreferences.getString("cep", "")

        binding.textViewCep.text = "cep: " + cep.orEmpty()

        controlVisibilityButton()

        editor.putString("cep", cep)

        editor.apply()

        onClickSendButton()
    }

    private fun controlVisibilityButton() {
        binding.radioButtonSim.isChecked = true
        binding.radioButtonSim.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.btnSend.visibility = View.VISIBLE
            }
        }

        binding.radioButtonNao.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.btnSend.visibility = View.GONE
            }
        }
    }

    private fun onClickSendButton() {
        binding.btnSend.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
            val cep = sharedPreferences.getString("cep", "")
            val logradouro = sharedPreferences.getString("logradouro", "")
            val bairro = sharedPreferences.getString("bairro", "")
            val localidade = sharedPreferences.getString("localidade", "")
            val uf = sharedPreferences.getString("uf", "")
            val ibge = sharedPreferences.getString("ibge", "")
            val ddd = sharedPreferences.getString("ddd", "")

            viewModel.sendLogsToDiscord(
                cep.orEmpty(),
                logradouro.orEmpty(),
                bairro.orEmpty(),
                localidade.orEmpty(),
                uf.orEmpty(),
                ibge.orEmpty(),
                ddd.orEmpty(),
                onLogsSendFailed = {
                    showFailedAlert()
                },
                onLogsSent = {
                    showSuccessAlert()
                }
            )
        }
    }

    private fun showFailedAlert() {
        val message = "Falha no envio de logs"
        requireActivity().runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showSuccessAlert() {
        val message = "Logs enviados com sucesso"
        requireActivity().runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
