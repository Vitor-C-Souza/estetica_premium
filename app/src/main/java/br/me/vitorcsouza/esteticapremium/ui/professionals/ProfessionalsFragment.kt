package br.me.vitorcsouza.esteticapremium.ui.professionals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.me.vitorcsouza.esteticapremium.R
import br.me.vitorcsouza.esteticapremium.databinding.FragmentProfessionalsBinding

class ProfessionalsFragment : Fragment() {

    private var _binding: FragmentProfessionalsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfessionalsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfessionalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.loadProfessionals()
    }

    private fun setupRecyclerView() {
        binding.rvProfessionals.layoutManager = LinearLayoutManager(context)
    }

    private fun observeViewModel() {
        viewModel.professionals.observe(viewLifecycleOwner) { list ->
            binding.rvProfessionals.adapter = ProfessionalAdapter(list) { professional ->
                findNavController().navigate(R.id.action_professionalsFragment_to_bookingFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}