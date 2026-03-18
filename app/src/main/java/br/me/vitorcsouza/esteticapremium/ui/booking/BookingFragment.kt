package br.me.vitorcsouza.esteticapremium.ui.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.me.vitorcsouza.esteticapremium.databinding.FragmentBookingBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BookingFragment : Fragment() {

    private var _binding: FragmentBookingBinding? = null
    private val binding get() = _binding!!

    private var calendar = Calendar.getInstance()
    private val today = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
        setupTimes()

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.ivPrevMonth.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            // Prevent going before current month
            if (calendar.get(Calendar.MONTH) < today.get(Calendar.MONTH) && 
                calendar.get(Calendar.YEAR) <= today.get(Calendar.YEAR)) {
                calendar = today.clone() as Calendar
            }
            updateUI()
        }

        binding.ivNextMonth.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            updateUI()
        }

        binding.btnConfirm.setOnClickListener {
            Toast.makeText(context, "Agendamento confirmado!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        updateMonthYearHeader()
        setupDates()
    }

    private fun updateMonthYearHeader() {
        val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale("pt", "BR"))
        binding.tvMonthYear.text = monthYearFormat.format(calendar.time).replaceFirstChar { it.uppercase() }
    }

    private fun setupDates() {
        val tempCalendar = calendar.clone() as Calendar
        val dates = mutableListOf<DateModel>()
        
        val dayFormat = SimpleDateFormat("EEE", Locale("pt", "BR"))
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // If it's the current month, start from today. Otherwise, start from the 1st.
        if (tempCalendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
            tempCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
            tempCalendar.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH))
        } else {
            tempCalendar.set(Calendar.DAY_OF_MONTH, 1)
        }

        // Generate days for the selected month (or next 14 days for simplicity in the slider)
        // Let's generate 14 days from the start point for a consistent UI
        for (i in 0 until 14) {
            val dayName = dayFormat.format(tempCalendar.time).uppercase().take(3)
            val dayNumber = tempCalendar.get(Calendar.DAY_OF_MONTH).toString()
            val fullDate = dateFormat.format(tempCalendar.time)
            
            dates.add(DateModel(dayName, dayNumber, fullDate, i == 0))
            tempCalendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        binding.rvDates.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvDates.adapter = DateAdapter(dates) { date ->
            // Handle date selection if needed
        }
    }

    private fun setupTimes() {
        val times = listOf(
            TimeModel("09:00"), TimeModel("09:30"), TimeModel("10:00"),
            TimeModel("10:30"), TimeModel("11:00"), TimeModel("11:30"),
            TimeModel("14:00"), TimeModel("14:30"), TimeModel("15:00"),
            TimeModel("15:30"), TimeModel("16:00"), TimeModel("16:30"),
            TimeModel("17:00")
        )

        binding.rvTimes.layoutManager = GridLayoutManager(context, 3)
        binding.rvTimes.adapter = TimeAdapter(times) { time ->
            // Handle time selection if needed
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}