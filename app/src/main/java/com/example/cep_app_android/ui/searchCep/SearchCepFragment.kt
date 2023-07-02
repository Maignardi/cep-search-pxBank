package com.example.cep_app_android.ui.searchCep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cep_app_android.databinding.FragmentSearchCepBinding

class SearchCepFragment : Fragment() {

    private var _binding: FragmentSearchCepBinding? = null
    private lateinit var viewModel: SearchCepViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchCepBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SearchCepViewModel::class.java]

        searchCep()

        return binding.root
    }

    private fun searchCep() {
        _binding?.btnSearch?.setOnClickListener {
            val cep = _binding?.editTextCep?.text.toString()
            context?.let { it1 -> viewModel.consultarCep(cep, it1) }
            _binding?.editTextCep?.text?.clear()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
