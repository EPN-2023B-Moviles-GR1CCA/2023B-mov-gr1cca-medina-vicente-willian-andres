package com.example.apprestaurante

import com.example.proyecto.Database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.Serializable

class RegistroParques():Serializable{
    val scope = CoroutineScope(Dispatchers.Default)
    public var db:Database
    init {
        db = Database()
    }

    fun addParque(parque:Parque){
        db.addParque(parque)
    }

    suspend fun getParque(id:Int): Parque? {
        return db.getParque(id)
    }

    fun updateParque(parque:Parque){
        db.updateParque(parque.numero, parque)
    }

    fun deleteParque(id:Int){
        db.deleteParque(id)
    }

    suspend fun getParques(): ArrayList<Parque> {
        return db.getParques()
    }
}