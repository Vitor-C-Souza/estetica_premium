package br.me.vitorcsouza.esteticapremium.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.me.vitorcsouza.esteticapremium.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        
        displayUserData()
        setupClickListeners()
    }

    private fun displayUserData() {
        val user = auth.currentUser
        user?.let {
            binding.tvUserName.text = it.displayName ?: "Usuário"
        }
    }

    private fun setupClickListeners() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}