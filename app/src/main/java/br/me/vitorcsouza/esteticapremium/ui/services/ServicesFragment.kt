package br.me.vitorcsouza.esteticapremium.ui.services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.me.vitorcsouza.esteticapremium.data.model.Service
import br.me.vitorcsouza.esteticapremium.databinding.FragmentServicesBinding

class ServicesFragment : Fragment() {

    private var _binding: FragmentServicesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ServiceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.loadServices()

        viewModel.services.observe(viewLifecycleOwner) { servicesList ->
            (binding.rvServices.adapter as? ServiceAdapter)?.updateList(servicesList)
        }

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun setupRecyclerView() {
        binding.rvServices.layoutManager = LinearLayoutManager(context)
        binding.rvServices.adapter = ServiceAdapter(emptyList()) { service ->
            onServiceClick(service)
        }
    }

    private fun onServiceClick(service: Service) {
        val action = ServicesFragmentDirections.actionServicesFragmentToProfessionalsFragment(
            serviceName = service.name
        )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
