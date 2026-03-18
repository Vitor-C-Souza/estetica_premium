package br.me.vitorcsouza.esteticapremium.data.model

data class Booking(
    val userId: String = "",
    val serviceName: String = "",
    val professionalName: String = "",
    val date: String = "",
    val time: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
