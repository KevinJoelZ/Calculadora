package com.example.calculadora

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.calculadora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var primerNumero = 0.0
    private var operacionActual = ""
    private var nuevoNumero = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarBotonesNumericos()
        configurarBotonesOperaciones()
    }

    private fun configurarBotonesNumericos() {
        val botonesNumericos = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3,
            binding.btn4, binding.btn5, binding.btn6, binding.btn7,
            binding.btn8, binding.btn9, binding.btnDecimal
        )

        botonesNumericos.forEach { boton ->
            boton.setOnClickListener {
                val numeroPresionado = boton.text.toString()
                if (nuevoNumero) {
                    binding.etDisplay.setText(numeroPresionado)
                    nuevoNumero = false
                } else {
                    binding.etDisplay.append(numeroPresionado)
                }
            }
        }
    }

    private fun configurarBotonesOperaciones() {
        binding.btnClear.setOnClickListener {
            binding.etDisplay.setText("0")
            primerNumero = 0.0
            operacionActual = ""
            nuevoNumero = true
        }

        val botonesOperaciones = mapOf(
            binding.btnAdd to "+",
            binding.btnSubtract to "-",
            binding.btnMultiply to "×",
            binding.btnDivide to "÷"
        )

        botonesOperaciones.forEach { (boton, operador) ->
            boton.setOnClickListener {
                primerNumero = binding.etDisplay.text.toString().toDouble()
                operacionActual = operador
                nuevoNumero = true
            }
        }

        binding.btnEquals.setOnClickListener {
            if (operacionActual.isNotEmpty()) {
                val segundoNumero = binding.etDisplay.text.toString().toDouble()
                val calculadora = Calculadora(primerNumero, segundoNumero)
                
                try {
                    val resultado = when (operacionActual) {
                        "+" -> calculadora.sumar()
                        "-" -> calculadora.restar()
                        "×" -> calculadora.multiplicar()
                        "÷" -> calculadora.dividir()
                        else -> 0.0
                    }

                    mostrarResultado(resultado)
                    primerNumero = resultado
                    nuevoNumero = true
                } catch (e: ArithmeticException) {
                    mostrarError("Error: ${e.message}")
                }
            }
        }
    }

    private fun mostrarResultado(resultado: Double) {
        val resultadoFormateado = if (resultado % 1 == 0.0) {
            resultado.toInt().toString()
        } else {
            resultado.toString()
        }
        
        AlertDialog.Builder(this)
            .setTitle("Resultado")
            .setMessage(resultadoFormateado)
            .setPositiveButton("OK") { dialog, _ ->
                binding.etDisplay.setText(resultadoFormateado)
                dialog.dismiss()
            }
            .show()
    }

    private fun mostrarError(mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(mensaje)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}