package com.example.mobile.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mobile.R
import com.example.mobile.core.di.ServiceLocator
import com.example.mobile.databinding.FragmentDashboardBinding
import com.example.mobile.ui.state.UiState

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels {
        ServiceLocator.dashboardViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionManager = ServiceLocator.sessionManager(requireContext())
        if (sessionManager.getAuthToken().isNullOrBlank()) {
            findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)
            return
        }

        binding.refreshButton.setOnClickListener {
            viewModel.loadProfile()
        }

        binding.logoutButton.setOnClickListener {
            sessionManager.clearSession()
            findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)
        }

        viewModel.profileState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.statusText.text = getString(R.string.status_loading)
                }
                is UiState.Success -> {
                    val profile = state.data
                    binding.statusText.text = ""
                    binding.usernameValue.text = profile.username
                    binding.emailValue.text = profile.email
                    binding.nameValue.text = listOfNotNull(profile.firstName, profile.lastName)
                        .joinToString(" ")
                        .ifBlank { getString(R.string.value_not_set) }
                    binding.userIdValue.text = profile.userId.toString()
                    binding.createdAtValue.text = profile.createdAt ?: getString(R.string.value_not_set)
                }
                is UiState.Error -> {
                    binding.statusText.text = state.message
                }
                UiState.Idle -> Unit
            }
        }

        viewModel.loadProfile()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
