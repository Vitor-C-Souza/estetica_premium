package br.me.vitorcsouza.esteticapremium.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.me.vitorcsouza.esteticapremium.R
import br.me.vitorcsouza.esteticapremium.databinding.FragmentRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

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

        auth = Firebase.auth

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnRegister.setOnClickListener {
            validateData()
        }

        binding.tvLogin.setOnClickListener {
            val controller = findNavController()
            if (controller.currentDestination?.id == R.id.registerFragment) {
                controller.navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    private fun validateData() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showToast("Preencha todos os campos")
            return
        }

        if (password != confirmPassword) {
            showToast("As senhas não conferem")
            return
        }

        if (password.length < 6) {
            showToast("A senha deve ter no mínimo 6 caracteres")
            return
        }

        registerFirebase(name, email, password)
    }

    private fun registerFirebase(name: String, email: String, password: String) {
        binding.btnRegister.isEnabled = false
        
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (!isAdded) return@addOnCompleteListener

                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                    }

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { updateTask ->
                            if (!isAdded) return@addOnCompleteListener
                            
                            if (updateTask.isSuccessful) {
                                showToast("Cadastro realizado com sucesso")
                                navigateToLogin()
                            } else {
                                val error = updateTask.exception?.message ?: "Erro ao atualizar perfil"
                                showToast("Erro: $error")
                                // Mesmo com erro no perfil, o usuário foi criado. 
                                // Pode-se decidir se navega ou não.
                                navigateToLogin()
                            }
                        }
                } else {
                    binding.btnRegister.isEnabled = true
                    val error = task.exception?.message ?: "Erro ao cadastrar"
                    showToast("Erro: $error")
                }
            }
    }

    private fun navigateToLogin() {
        val controller = findNavController()
        if (controller.currentDestination?.id == R.id.registerFragment) {
            controller.navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}