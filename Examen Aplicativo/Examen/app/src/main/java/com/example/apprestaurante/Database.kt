package com.example.proyecto

import android.util.Log
import com.example.apprestaurante.Flora
import com.example.apprestaurante.Parque
import com.google.firebase.Firebase
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.firestore
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class Database {

    fun addParque(parque:Parque){
        val db = Firebase.firestore
        val newData = mapOf(
            "numero" to parque.numero,
            "nombre" to parque.nombre,
            "direccion" to parque.direccion,
            "responsable" to parque.responsable,
            "flora" to parque.flora,
        )
        db.collection("Parques")
            .add(newData)
            .addOnSuccessListener { documentReference ->
                Log.d("MINEWM", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("MINEWM", "Error adding document", e)
            }
    }

    fun updateParque(id:Int, parque:Parque){
        val db = Firebase.firestore
        val newData = mapOf(
            "nombre" to parque.nombre,
            "direccion" to parque.direccion,
            "responsable" to parque.responsable,
            "flora" to parque.flora,
        )

        db.collection("Parques").whereEqualTo("numero", id).limit(1)
            .get()
            .addOnSuccessListener { parque ->
                db.collection("Parques").document(parque.first().id)
                    .update(newData)
                    .addOnSuccessListener { Log.d("MINEWM", "DocumentSnapshot successfully updated!") }
                    .addOnFailureListener { e -> Log.w("MINEWM", "Error updating document", e) }
            }
            .addOnFailureListener { exception -> }
    }

    fun deleteParque(id:Int){
        val db = Firebase.firestore
        db.collection("Parques").whereEqualTo("numero", id).limit(1)
            .get()
            .addOnSuccessListener { parque ->
                db.collection("Parques").document(parque.first().id)
                    .delete()
                    .addOnSuccessListener { Log.d("MINEWM", "DocumentSnapshot successfully deleted!") }
                    .addOnFailureListener { e -> Log.w("MINEWM", "Error deleting document", e) }
            }
            .addOnFailureListener { exception -> }
    }

    suspend fun getParque(id:Int):Parque{
        return suspendCoroutine { continuation ->
            val db = Firebase.firestore
            db.collection("Parques").whereEqualTo("numero", id).limit(1)
                .get()
                .addOnSuccessListener { parque ->
                    var numero:Int
                    try {
                        numero = parque.first().data?.get("numero")?.toString()?.toInt() ?: 0
                    } catch (e: NumberFormatException){
                        numero = 0
                    }

                    var nuevoParque = Parque(
                        numero,
                        parque.first().data?.get("nombre").toString(),
                        parque.first().data?.get("direccion").toString(),
                        parque.first().data?.get("responsable").toString()
                    )
                    try {
                        var aux = parque.first().data?.get("flora") as ArrayList<HashMap<Any, Any>>
                        for (flora in aux){
                            var numero2:Int
                            try {
                                numero2 = flora["numero"].toString().toInt()
                            } catch (e: NumberFormatException){
                                numero2 = 0
                            }
                            nuevoParque.flora.add(Flora(
                                numero2,
                                flora["nombre"].toString(),
                                flora["tipo"].toString(),
                                flora["ubicacion"].toString(),
                                flora["rerencia"].toString()
                            ))
                        }
                    } catch (e: Exception){
                        nuevoParque.flora = ArrayList<Flora>()
                    }
                    continuation.resume(nuevoParque)
                }
                .addOnFailureListener { exception ->
                    var nuevoParque = Parque(
                        1,
                        "No hay datos",
                        "No hay datos",
                        "No hay datos"
                    )
                    continuation.resume(nuevoParque)
                }
        }
    }

    fun qsToParque(parque: QueryDocumentSnapshot):Parque{
        // ciudad.id
        var numero:Int
        try {
            numero = parque.data["numero"].toString().toInt()
        } catch (e: NumberFormatException){
            numero = 0
        }

        var nuevoParque = Parque(
            numero,
            parque.data["nombre"].toString(),
            parque.data["direccion"].toString(),
            parque.data["responsable"].toString()
        )
        try {
            var aux = parque.data["flora"] as ArrayList<HashMap<Any, Any>>
            for (flora in aux){
                var numero2:Int
                try {
                    numero2 = flora["numero"].toString().toInt()
                } catch (e: NumberFormatException){
                    numero2 = 0
                }
                nuevoParque.flora.add(Flora(
                    numero2,
                    flora["nombre"].toString(),
                    flora["tipo"].toString(),
                    flora["ubicacion"].toString(),
                    flora["rerencia"].toString()
                ))
            }
        } catch (e: Exception){
            Log.i("MINEWM", "qsToParque: ${e}")
            nuevoParque.flora = ArrayList<Flora>()
        }
        return nuevoParque

    }



    suspend fun getParques():ArrayList<Parque>{
        return suspendCoroutine { continuation ->
            val db = Firebase.firestore
            val parques = ArrayList<Parque>()
            db.collection("Parques")
                .get()
                .addOnSuccessListener { result ->
                    for (parque in result){
                        parques.add(qsToParque(parque))
                    }
                    continuation.resume(parques)
                }
                .addOnFailureListener { exception ->
                    continuation.resume(ArrayList<Parque>())
                }
        }


    }
}