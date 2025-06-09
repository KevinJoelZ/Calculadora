package com.example.calculadora

class Calculadora(private var num1: Double, private var num2: Double) : IOperaciones {
    
    override fun sumar(): Double {
        return num1 + num2
    }

    override fun restar(): Double {
        return num1 - num2
    }

    override fun multiplicar(): Double {
        return num1 * num2
    }

    override fun dividir(): Double {
        if (num2 != 0.0) {
            return num1 / num2
        }
        throw ArithmeticException("No se puede dividir por cero")
    }
} 