package br.me.vitorcsouza.esteticapremium.ui.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.me.vitorcsouza.esteticapremium.R
import br.me.vitorcsouza.esteticapremium.databinding.FragmentBookingConfirmationBinding
import kotlin.getValue

class BookingConfirmationFragment : Fragment() {

    private var _binding: FragmentBookingConfirmationBinding? = null
    private val binding get() = _binding!!

    private val args: BookingConfirmationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvProfessionalName.text = args.professionalName
        binding.tvDate.text = args.date
        binding.tvTime.text = args.time
        binding.tvLocation.text = "Av. Paulista, 1000"

        binding.btnBackToHome.setOnClickListener {
            findNavController().navigate(R.id.action_global_homeFragment)
        }

        binding.btnViewBookings.setOnClickListener {
            findNavController().navigate(R.id.action_bookingConfirmationFragment_to_bookingsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}