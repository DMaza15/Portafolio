package com.maza.lab02_dm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                RegistroNotas()
            }
        }
    }
}

@Composable
fun RegistroNotas() {

    // Estados de las 4 notas
    var fundamentos by remember { mutableFloatStateOf(0f) }
    var poo by remember { mutableFloatStateOf(0f) }
    var moviles by remember { mutableFloatStateOf(0f) }
    var baseDatos by remember { mutableFloatStateOf(0f) }

    // Estados del Switch y Checkbox
    var redondear by remember { mutableStateOf(false) }
    var confirmado by remember { mutableStateOf(false) }
    var mostrarResultado by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // Barra superior
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Registro de Notas",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Curso 1
            CursoSlider(
                nombre = "Fundamentos de Programación",
                peso = 20,
                nota = fundamentos,
                onNotaChange = {
                    fundamentos = it
                    mostrarResultado = false
                }
            )

            // Curso 2
            CursoSlider(
                nombre = "Programación Orientada a Objetos",
                peso = 25,
                nota = poo,
                onNotaChange = {
                    poo = it
                    mostrarResultado = false
                }
            )

            // Curso 3
            CursoSlider(
                nombre = "Programación en Móviles",
                peso = 30,
                nota = moviles,
                onNotaChange = {
                    moviles = it
                    mostrarResultado = false
                }
            )

            // Curso 4
            CursoSlider(
                nombre = "Base de Datos",
                peso = 25,
                nota = baseDatos,
                onNotaChange = {
                    baseDatos = it
                    mostrarResultado = false
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Switch(
                    checked = redondear,
                    onCheckedChange = {
                        redondear = it
                        mostrarResultado = false
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("Redondear promedio final")
            }

            // Checkbox
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = confirmado,
                    onCheckedChange = {
                        confirmado = it
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("Confirmo que las notas son correctas")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón
            Button(
                onClick = {
                    mostrarResultado = true
                },
                enabled = confirmado,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("CALCULAR PROMEDIO")
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (!mostrarResultado) {

                Text(
                    text = "Asigna las notas y confirma para calcular",
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth()
                )

            } else {

                // Promedio ponderado
                val promedioPonderado =
                    fundamentos * 0.20f +
                            poo * 0.25f +
                            moviles * 0.30f +
                            baseDatos * 0.25f

                // Promedio final
                val promedioFinal =
                    if (redondear) {
                        promedioPonderado.roundToInt().toFloat()
                    } else {
                        promedioPonderado
                    }

                // Observación
                val observacion: String
                val colorChip: Color

                when {
                    promedioFinal >= 17 -> {
                        observacion = "EXCELENTE"
                        colorChip = Color(0xFF1B5E20)
                    }

                    promedioFinal >= 13 -> {
                        observacion = "APROBADO"
                        colorChip = Color(0xFF4CAF50)
                    }

                    promedioFinal >= 10 -> {
                        observacion = "EN RECUPERACIÓN"
                        colorChip = Color(0xFFFFB300)
                    }

                    else -> {
                        observacion = "DESAPROBADO"
                        colorChip = Color(0xFFD32F2F)
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "Resultado",
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Promedio ponderado: ${
                                String.format(
                                    "%.2f",
                                    promedioPonderado
                                )
                            }"
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text =
                                if (redondear) {
                                    "Promedio final: ${
                                        promedioFinal.roundToInt()
                                    } (redondeado)"
                                } else {
                                    "Promedio final: ${
                                        String.format(
                                            "%.2f",
                                            promedioFinal
                                        )
                                    }"
                                }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Surface(
                            color = colorChip,
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                text = observacion,
                                color = Color.White,
                                modifier = Modifier.padding(
                                    horizontal = 16.dp,
                                    vertical = 8.dp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "✓ Promedio calculado correctamente",
                            color = Color(0xFF2E7D32)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Desarrollado por: DARIO MAZA",
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun CursoSlider(
    nombre: String,
    peso: Int,
    nota: Float,
    onNotaChange: (Float) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "$nombre ($peso%)",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium
            )

            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = nota.toInt().toString(),
                    modifier = Modifier.padding(
                        horizontal = 10.dp,
                        vertical = 5.dp
                    )
                )
            }
        }

        Slider(
            value = nota,
            onValueChange = onNotaChange,
            valueRange = 0f..20f,
            steps = 19,
            modifier = Modifier.fillMaxWidth()
        )
    }

    Spacer(modifier = Modifier.height(8.dp))
}