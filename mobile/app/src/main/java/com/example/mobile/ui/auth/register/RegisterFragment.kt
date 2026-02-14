package com.example.mobile.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mobile.R
import com.example.mobile.core.di.ServiceLocator
import com.example.mobile.databinding.FragmentRegisterBinding
import com.example.mobile.ui.auth.AuthViewModel
import com.example.mobile.ui.state.UiState

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels {
        ServiceLocator.authViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            binding.statusText.text = ""
            val username = binding.usernameInput.text?.toString()?.trim().orEmpty()
            val email = binding.emailInput.text?.toString()?.trim().orEmpty()
            val password = binding.passwordInput.text?.toString()?.trim().orEmpty()
            val firstName = binding.firstNameInput.text?.toString()?.trim().orEmpty().ifEmpty { null }
            val lastName = binding.lastNameInput.text?.toString()?.trim().orEmpty().ifEmpty { null }

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                binding.statusText.text = getString(R.string.error_required_fields)
                return@setOnClickListener
            }

            viewModel.register(username, email, password, firstName, lastName)
        }

        binding.loginLink.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.registerState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.registerButton.isEnabled = false
                    binding.statusText.text = getString(R.string.status_loading)
                }
                is UiState.Success -> {
                    binding.registerButton.isEnabled = true
                    binding.statusText.text = getString(R.string.status_register_success)
                    viewModel.resetRegisterState()
                    findNavController().navigateUp()
                }
                is UiState.Error -> {
                    binding.registerButton.isEnabled = true
                    binding.statusText.text = state.message
                }
                UiState.Idle -> {
                    binding.registerButton.isEnabled = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
