package com.example.apprestaurante

import java.io.Serializable

data class Parque(
    var numero: Int,
    var nombre: String,
    var direccion: String,
    var responsable: String,
) : Serializable {
    public var flora: ArrayList<Flora>

    init {
        this.numero = numero;
        this.nombre = nombre
        this.direccion = direccion
        this.responsable = responsable
        this.flora = arrayListOf<Flora>();
    }

    constructor(park:Parque):this(park.numero, park.nombre, park.direccion, park.responsable){}

    fun whoiam():String{
        return "Parque '${this.nombre}' con ${flora.size} elementos"
    }

    fun getIndexofFlora(id:Int): Int {
        return flora.indexOfFirst { it.numero == id }
    }

    fun getFlora(id:Int): Flora? {
        return flora.find { it.numero == id }
    }

    fun addFlora(element:Flora){
        flora.add(element)
    }

    fun deleteFlora(id:Int){
        val idfinded = getIndexofFlora(id)
        flora.removeAt(idfinded)
    }

    fun updateFlora(flora:Flora){
        val idfinded = getIndexofFlora(flora.numero)
        this.flora[idfinded] = flora
    }
}