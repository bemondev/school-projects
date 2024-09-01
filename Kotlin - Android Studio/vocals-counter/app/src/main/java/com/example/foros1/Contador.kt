package com.example.foros1

fun main() {
    do {
        println("\n¿Qué deseas hacer?")
        println("1. Contar vocales en una cadena")
        println("2. Salir")

        print("Selecciona una opción (1-2): ")
        val opcion = readLine()?.toIntOrNull()

        // Estructura when para manejar la opción seleccionada
        when (opcion) {
            1 -> contador()
            2 -> println("Saliendo del menú...")
            else -> println("Opción no válida, por favor intenta nuevamente.")  // Mensaje para opción no válida
        }
    } while (opcion != 2)
}

fun contador(){
    try {
        println("")
        println("Ingrese una cadena de caracteres:");
        val text = readLine() ?: ""
        if(text != null) {
            val vocales ="aeiou";
            var contadorVocales = 0;
            for(char in text.lowercase()) {
                if (char in vocales) {
                    contadorVocales++
                }
            }

            println("El número total de vocales en tu texto es: $contadorVocales")
        } else {
            println("La cadena no es válida")
        }
    } catch (e: Exception) {
        println("Ocurrió un error: ${e.message}, intente nuevamente.")
        contador()
    }
}
