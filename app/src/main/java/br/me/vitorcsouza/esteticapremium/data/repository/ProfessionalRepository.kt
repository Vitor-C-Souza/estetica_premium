package br.me.vitorcsouza.esteticapremium.data.repository

import br.me.vitorcsouza.esteticapremium.data.model.Professional
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProfessionalRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getProfessionals(): List<Professional> {
        return try {
            db.collection("professionals")
                .get()
                .await()
                .toObjects(Professional::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}