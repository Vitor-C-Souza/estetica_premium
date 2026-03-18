package br.me.vitorcsouza.esteticapremium.data.repository

import br.me.vitorcsouza.esteticapremium.data.model.Service
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ServiceRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getServices(): List<Service> {
        return try {
            db.collection("services")
                .get()
                .await()
                .toObjects(Service::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}