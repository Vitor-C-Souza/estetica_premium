package br.me.vitorcsouza.esteticapremium.ui.booking

data class DateModel(
    val dayName: String,
    val dayNumber: String,
    val fullDate: String,
    var isSelected: Boolean = false
)

data class TimeModel(
    val time: String,
    var isSelected: Boolean = false
)