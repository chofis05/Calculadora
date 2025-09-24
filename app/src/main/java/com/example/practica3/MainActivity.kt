package com.example.practica3

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnborrar = findViewById<Button>(R.id.btn_borrar)
        val btnlimpiar = findViewById<Button>(R.id.btn_limpiar)
        val btn9 = findViewById<Button>(R.id.btn_9)
        val btn8 = findViewById<Button>(R.id.btn_8)
        val btn7 = findViewById<Button>(R.id.btn_7)
        val btn6 = findViewById<Button>(R.id.btn_6)
        val btn5 = findViewById<Button>(R.id.btn_5)
        val btn4 = findViewById<Button>(R.id.btn_4)
        val btn3 = findViewById<Button>(R.id.btn_3)
        val btn2 = findViewById<Button>(R.id.btn_2)
        val btn1 = findViewById<Button>(R.id.btn_1)
        val btn0 = findViewById<Button>(R.id.btn_0)
        val btnsuma = findViewById<Button>(R.id.btn_suma)
        val btnresta = findViewById<Button>(R.id.btn_resta)
        val btncalcular = findViewById<Button>(R.id.btn_calcular)
        val btnmultiplicacion = findViewById<Button>(R.id.btn_multiplicacion)
        val btndivision = findViewById<Button>(R.id.btn_division)
        val btndecimales = findViewById<Button>(R.id.btn_decimales)
        val txtresultado = findViewById<TextView>(R.id.txt_resultado)

        var operacion: String = ""
        var numeroanterior: String = ""
        var nuevonumero: String = ""
        var reiniciar = false

        fun actualizarResultado(valor: String) {
            if (reiniciar) {
                nuevonumero = ""
                reiniciar = false
            }

            if (valor == "-" && nuevonumero.isEmpty()) {
                nuevonumero = "-"
                txtresultado.text = nuevonumero
                return
            }

            if (txtresultado.text == "0.0" && valor != "0") {
                nuevonumero = valor
            } else {
                nuevonumero += valor
            }
            txtresultado.text = nuevonumero
        }


        fun asignarOperacion(op: String) {
            if (nuevonumero.isNotEmpty()) {
                numeroanterior = nuevonumero
                nuevonumero = ""
            }
            operacion = op
        }

        val botonesNumericos = listOf(btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9)
        botonesNumericos.forEachIndexed { index, button ->
            button.setOnClickListener {
                actualizarResultado(index.toString())
            }
        }

        btndecimales.setOnClickListener {
            if (!nuevonumero.contains(".")) {
                if (nuevonumero.isEmpty()) {
                    actualizarResultado("0.")
                } else {
                    actualizarResultado(".")
                }
            }
        }

        btnsuma.setOnClickListener {
            asignarOperacion("+")
        }
        btnresta.setOnClickListener {
            if (nuevonumero.isEmpty() && operacion.isEmpty()) {
                actualizarResultado("-")
            } else {
                asignarOperacion("-")
            }
        }
        btnmultiplicacion.setOnClickListener {
            asignarOperacion("*")
        }
        btndivision.setOnClickListener {
            asignarOperacion("/")
        }

        btncalcular.setOnClickListener {
            if (numeroanterior.isEmpty() || nuevonumero.isEmpty()) {
                txtresultado.text = "ERROR"
                return@setOnClickListener
            }

            val num1 = numeroanterior.toDoubleOrNull()
            val num2 = nuevonumero.toDoubleOrNull()

            if (num1 == null || num2 == null) {
                txtresultado.text = "Entrada inválida"
                return@setOnClickListener
            }

            val resultado = when (operacion) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "*" -> num1 * num2
                "/" -> {
                    if (num2 == 0.0) {
                        txtresultado.text = "Pongale error si no le gusta division por 0"
                        return@setOnClickListener
                    } else {
                        num1 / num2
                    }
                }
                else -> {
                    txtresultado.text = "Operación inválida"
                    return@setOnClickListener
                }
            }

            val resultadoTexto = resultado.toString()

            val resultadoFinal = if (!resultadoTexto.contains(".")) {
                resultadoTexto + ".0"
            } else {
                resultadoTexto
            }

            txtresultado.text = resultadoFinal
            nuevonumero = resultadoFinal
            numeroanterior = ""
            operacion = ""
            reiniciar = true
        }

        btnborrar.setOnClickListener {
                if (nuevonumero.isNotEmpty()) {
                    nuevonumero = nuevonumero.dropLast(1)
                }

                if (nuevonumero.isEmpty()) {
                    txtresultado.text = "0.0"
                } else {
                    txtresultado.text = nuevonumero
                }

            }

            btnlimpiar.setOnClickListener {
                numeroanterior = ""
                nuevonumero = ""
                operacion = ""
                txtresultado.text = "0.0"
            }
        }
    }

