package br.me.vitorcsouza.esteticapremium.ui.booking

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.me.vitorcsouza.esteticapremium.R
import br.me.vitorcsouza.esteticapremium.databinding.FragmentBookingBinding
import br.me.vitorcsouza.esteticapremium.receiver.BookingNotificationReceiver
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BookingFragment : Fragment() {

    private val viewModel: BookingViewModel by viewModels()
    private var _binding: FragmentBookingBinding? = null
    private val binding get() = _binding!!

    private val args: BookingFragmentArgs by navArgs()

    private var calendar = Calendar.getInstance()
    private val today = Calendar.getInstance()
    
    private var selectedDate: DateModel? = null
    private var selectedTime: TimeModel? = null

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
            showConfirmationDialog()
        }

        viewModel.isSaved.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Agendamento confirmado!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_global_homeFragment)
            } else {
                Toast.makeText(requireContext(), "Erro ao agendar. Tente novamente.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showConfirmationDialog() {
        val dateText = selectedDate?.let { "${it.dayNumber} de ${binding.tvMonthYear.text}" } ?: "Data não selecionada"
        val timeText = selectedTime?.time ?: "Horário não selecionado"

        AlertDialog.Builder(requireContext())
            .setTitle("Confirmar Agendamento")
            .setMessage("Deseja confirmar seu agendamento para o dia $dateText às $timeText?")
            .setPositiveButton("Confirmar") { _, _ ->
                scheduleNotification()
                navigateToConfirmation(dateText, timeText)
                
                viewModel.confirmBooking(
                    service = args.serviceName,
                    professional = args.professionalName, 
                    date = dateText,
                    time = timeText
                )
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun scheduleNotification() {
        val date = selectedDate ?: return
        val time = selectedTime ?: return

        val bookingCalendar = Calendar.getInstance()
        try {
            val dateParts = date.fullDate.split("-")
            val timeParts = time.time.split(":")
            
            bookingCalendar.set(Calendar.YEAR, dateParts[0].toInt())
            bookingCalendar.set(Calendar.MONTH, dateParts[1].toInt() - 1)
            bookingCalendar.set(Calendar.DAY_OF_MONTH, dateParts[2].toInt())
            bookingCalendar.set(Calendar.HOUR_OF_DAY, timeParts[0].toInt())
            bookingCalendar.set(Calendar.MINUTE, timeParts[1].toInt())
            bookingCalendar.set(Calendar.SECOND, 0)

            val notificationTime = bookingCalendar.timeInMillis - (30 * 60 * 1000)

            if (notificationTime <= System.currentTimeMillis()) {
                return
            }

            val intent = Intent(requireContext(), BookingNotificationReceiver::class.java).apply {
                putExtra("serviceName", args.serviceName)
                putExtra("time", time.time)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                requireContext(),
                bookingCalendar.timeInMillis.toInt(),
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        notificationTime,
                        pendingIntent
                    )
                } else {
                    val intentPermission = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                    startActivity(intentPermission)
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    notificationTime,
                    pendingIntent
                )
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun navigateToConfirmation(date: String, time: String) {
        val action = BookingFragmentDirections.actionBookingFragmentToBookingConfirmationFragment(
            professionalName = args.professionalName,
            date = date,
            time = time
        )
        findNavController().navigate(action)
    }

    private fun updateUI() {
        updateMonthYearHeader()
        setupDates()
    }

    private fun updateMonthYearHeader() {
        val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.forLanguageTag("pt-BR"))
        binding.tvMonthYear.text = monthYearFormat.format(calendar.time).replaceFirstChar { it.uppercase() }
    }

    private fun setupDates() {
        val tempCalendar = calendar.clone() as Calendar
        val dates = mutableListOf<DateModel>()
        
        val dayFormat = SimpleDateFormat("EEE", Locale.forLanguageTag("pt-BR"))
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        if (tempCalendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
            tempCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
            tempCalendar.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH))
        } else {
            tempCalendar.set(Calendar.DAY_OF_MONTH, 1)
        }

        for (i in 0 until 14) {
            val dayName = dayFormat.format(tempCalendar.time).uppercase().take(3)
            val dayNumber = tempCalendar.get(Calendar.DAY_OF_MONTH).toString()
            val fullDate = dateFormat.format(tempCalendar.time)
            
            val dateModel = DateModel(dayName, dayNumber, fullDate, i == 0)
            if (i == 0) selectedDate = dateModel
            
            dates.add(dateModel)
            tempCalendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        binding.rvDates.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvDates.adapter = DateAdapter(dates) { date ->
            selectedDate = date
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
            selectedTime = time
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}