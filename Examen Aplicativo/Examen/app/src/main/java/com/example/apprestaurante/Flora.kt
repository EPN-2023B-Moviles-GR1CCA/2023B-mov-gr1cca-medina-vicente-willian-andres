package com.example.apprestaurante

import java.io.Serializable
import java.util.ArrayList

class Flora(
    var numero: Int,
    var nombre: String,
    var tipo: String,
    var ubicacion: String,
    var referencia: String
):Serializable{

    init {this.numero = numero;this.nombre = nombre;this.tipo = tipo;this.ubicacion = ubicacion;this.referencia = referencia }

    constructor(flora: Flora):this(flora.numero, flora.nombre, flora.tipo, flora.ubicacion, flora.referencia){}

    fun whoiam():String{
        return("Nombre: '${this.nombre}', tipo: ${this.tipo}")
    }

    fun toMap(): Map<String, Any>{
        return mapOf(
            "numero" to this.numero,
            "nombre" to this.nombre,
            "tipo" to this.tipo,
            "ubicacion" to this.ubicacion,
            "referencia" to this.referencia,
        )
    }


    fun showDetails(){
        println("\t\tINFORMACIÓN DEL ELEMTO")
        println("\t\t\tFlora: ${nombre}")
        println("\t\t\tCoordenadas en: X:${this.ubicacion}")
        println("\t\t\tTipo: ${this.tipo}")
        println("\t\t\tReferencia: ${this.referencia}")
    }
}