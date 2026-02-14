package com.example.mobile.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mobile.R
import com.example.mobile.core.di.ServiceLocator
import com.example.mobile.databinding.FragmentLoginBinding
import com.example.mobile.ui.auth.AuthViewModel
import com.example.mobile.ui.state.UiState

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels {
        ServiceLocator.authViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            binding.statusText.text = ""
            val identifier = binding.identifierInput.text?.toString()?.trim().orEmpty()
            val password = binding.passwordInput.text?.toString()?.trim().orEmpty()
            if (identifier.isEmpty() || password.isEmpty()) {
                binding.statusText.text = getString(R.string.error_required_fields)
                return@setOnClickListener
            }
            viewModel.login(identifier, password)
        }

        binding.registerLink.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.loginButton.isEnabled = false
                    binding.statusText.text = getString(R.string.status_loading)
                }
                is UiState.Success -> {
                    binding.loginButton.isEnabled = true
                    binding.statusText.text = getString(R.string.status_login_success)
                    findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                }
                is UiState.Error -> {
                    binding.loginButton.isEnabled = true
                    binding.statusText.text = state.message
                }
                UiState.Idle -> {
                    binding.loginButton.isEnabled = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
