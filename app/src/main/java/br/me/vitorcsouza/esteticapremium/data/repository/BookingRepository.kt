package br.me.vitorcsouza.esteticapremium.data.repository

import br.me.vitorcsouza.esteticapremium.data.model.Booking
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class BookingRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun saveBooking(booking: Booking): Boolean {
        return try {
            val currentUserId = auth.currentUser?.uid ?: return false

            val bookingWithUserId = booking.copy(userId = currentUserId)

            db.collection("bookings")
                .add(bookingWithUserId)
                .await()

            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getMyBookings(): List<Booking> {
        val currentUserId = auth.currentUser?.uid ?: return emptyList()

        return try {
            db.collection("bookings")
                .whereEqualTo("userId", currentUserId)
                .get()
                .await()
                .toObjects(Booking::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}