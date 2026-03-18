package br.me.vitorcsouza.esteticapremium.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.me.vitorcsouza.esteticapremium.R
import br.me.vitorcsouza.esteticapremium.databinding.FragmentLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

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

        auth = Firebase.auth

        setupClickListeners()
    }

    override fun onStart() {
        super.onStart()
        // Verifica se o usuário já está logado ao iniciar o fragmento
        auth.currentUser?.let {
            goToHome()
        }
    }

    private fun loginUser(email: String, password: String) {
        binding.btnLogin.isEnabled = false
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (!isAdded) return@addOnCompleteListener
                
                binding.btnLogin.isEnabled = true
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Login realizado com sucesso", Toast.LENGTH_SHORT)
                        .show()
                    goToHome()
                } else {
                    val errorMessage = task.exception?.message ?: "Erro desconhecido"
                    Toast.makeText(
                        requireContext(),
                        "Erro no login: $errorMessage",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun setupClickListeners() {
        binding.tvCreateAccount.setOnClickListener {
            val controller = findNavController()
            if (controller.currentDestination?.id == R.id.loginFragment) {
                controller.navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToHome() {
        val controller = findNavController()
        if (controller.currentDestination?.id == R.id.loginFragment) {
            controller.navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}