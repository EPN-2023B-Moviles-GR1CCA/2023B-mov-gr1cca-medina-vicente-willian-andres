import java.util.*
import kotlin.collections.ArrayList

fun main(args: Array<String>) {
    println("Hola mundo!")

    // INMUTABLES (NO se reasignan "=")
    val inmutable: String = "Willian"
    // inmutable = "Andrés"

    // Mutables (Re asignar)
    var mutable: String = "Willian"
    mutable = "Andrés"

    //val > var
    // Duck Typing
    var ejemploVariable = "Willian Medina"
    val edadEjemplo: Int = 12
    ejemploVariable.trim()
    //ejemploVariable = edadEjemplo;

    //Variable primitiva
    val nombreEstudiante: String = "Willian Medina"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true
    //Clases Java
    val fechaNacimiento: Date = Date()

    //SWITCH
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        ("S") -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }

    val coqueteo = if (estadoCivilWhen == "S") "Si" else "No"

    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00)
    calcularSueldo(10.00, 12.00, 20.00)

    //Parametros nombrados
    calcularSueldo(sueldo = 10.00)
    calcularSueldo(sueldo = 10.00, bonoEspecial = 15.00)
    calcularSueldo(sueldo = 10.00, bonoEspecial = 12.00, tasa = 20.00)

    calcularSueldo(sueldo = 10.00, bonoEspecial = 20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00)

    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)

    val sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)



    // ARREGLOS
    // Arreglo estático
    val arregloEstatico: Array<Int> = arrayOf(1,2,3)
    println(arregloEstatico)

    // Arreglo dinámico
    val arregloDinamico: ArrayList<Int> = arrayListOf(
        1,2,3,4,5,6,7,8,9,10
    )
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //For each -> Unit
    val respuestaForEach: Unit = arregloDinamico.forEach {
        valorActual: Int ->
            println("Valor actual: ${valorActual}")
    }

    arregloDinamico.forEach { println(it) }

    arregloEstatico.forEachIndexed { indice:Int, valorActual:Int ->
        println("Valor ${valorActual} Indice: ${indice}")
    }

    println(respuestaForEach)

    val respuestaMap: List<Double> = arregloDinamico.map{
        valorActual: Int ->
            return@map valorActual.toDouble()+100.00
    }
    println(respuestaMap)
    var respuestaMapDos = arregloDinamico.map{ it + 15 }

    val respuestaFilter: List<Int> = arregloDinamico.filter {
        valorActual: Int ->
            val mayoresACinco: Boolean = valorActual > 5
        return@filter mayoresACinco
    }

    val respuestaFilterDos = arregloDinamico.filter { it <= 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    // any (al menos 1)
    // all (todos deben cumplir)
    val respuestaAny: Boolean = arregloDinamico.any{
        valorActual: Int ->
            return@any (valorActual > 5)
    }
    println(respuestaAny)

    val respuestaAll: Boolean = arregloDinamico.all {
        valorActual: Int ->
            return@all (valorActual > 5)
    }

    //REDUCE -> Valor acumulado
    // siempre inicia en 0
    val respuestaReduce:Int = arregloDinamico.reduce {
         acumulado:Int, valorActual:Int ->
            return@reduce (acumulado+valorActual)
    }

    println(respuestaReduce)


}

fun imrpimirNombre(nombre: String): Unit {
    println("Nombre: ${nombre}")
}

//fun imrpimirNombre(nombre: String){
//    println("Nombre: ${nombre}")
//}

fun calcularSueldo (
    sueldo: Double, //Requerido
    tasa: Double = 12.00, //Opcional
    bonoEspecial: Double?=null, //Opcion null -> nullable
): Double {
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        bonoEspecial.dec()
        return sueldo * (100/tasa) + bonoEspecial
    }
}

abstract class Numeros(
    protected val numeroUno: Int,
    protected val numeroDos: Int
){
    init{
        this.numeroUno; this.numeroDos
        numeroUno; numeroDos
        println("Inicializando")
    }
}

class Suma(
    uno: Int,
    dos: Int
): Numeros(uno, dos){
    init {
        this.numeroUno; numeroUno;
        this.numeroDos; numeroDos;
    }

    constructor(
        uno: Int?,
        dos: Int
    ) : this( // llamada constructor primario
        if(uno == null) 0 else uno,
        dos
    ){ //si necesitamos bloque de codigo lo usamos
        numeroUno
    }

    constructor(
        uno: Int,
        dos: Int?
    ) : this( // llamada constructor primario
        uno,
        if(dos==null) 0 else uno
    )

    constructor(
        uno: Int?,
        dos: Int?
    ): this(
        if(uno == null) 0 else uno,
        if(dos == null) 0 else uno
    )

    public fun sumar(): Int {
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }

    companion object { //Atributos y metodos "Compartidos"
        val pi = 3.14
        fun elevarAlCuadrado(num:Int): Int {
            return num*num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma: Int){
            historialSumas.add(valorNuevaSuma)
        }
    }
}
