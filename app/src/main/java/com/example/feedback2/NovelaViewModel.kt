package com.example.feedback2

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class NovelaViewModel : ViewModel() {

    //Lista de novelas en memoria
    private val _novelas = mutableStateListOf<Novela>()
    val novelas: List<Novela> get() = _novelas

    private val db: FirebaseFirestore = Firebase.firestore

    //Función para cargar novelas desde Firebase
    suspend fun cargarNovelas() {
        try {
            val documentos = db.collection("Novelas").get().await()
            _novelas.clear() //Limpiar la lista antes de cargar nuevas novelas
            for (documento in documentos) {
                val novela = documento.toObject(Novela::class.java)
                _novelas.add(novela)
            }
        } catch (e: Exception) {
            //Manejo de errores
            e.printStackTrace()
        }
    }

    fun agregarNovela(novela: Novela) {
        _novelas.add(novela)
    }

    fun eliminarNovela(novela: Novela) {
        _novelas.remove(novela)
    }

    fun marcarFavorita(novela: Novela) {
        val index = _novelas.indexOf(novela)
        if (index >= 0) {
            _novelas[index] = _novelas[index].copy(esFavorita = !novela.esFavorita)
        }
    }

    fun agregarResena(novela: Novela, resena: String) {
        novela.resenas.add(resena)
    }
}