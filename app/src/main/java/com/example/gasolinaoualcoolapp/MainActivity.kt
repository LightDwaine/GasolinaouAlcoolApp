package com.example.gasolinaoualcoolapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gasolinaoualcoolapp.ui.theme.GasolinaOuAlcoolAppTheme


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GasolinaOuAlcoolAppTheme (dynamicColor = false){
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold { innerPadding ->
                        MainScreen(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var gasolinaPrice by rememberSaveable { mutableStateOf("") }
    var alcoolPrice by rememberSaveable { mutableStateOf("") }
    var is75PercentEfficient by rememberSaveable { mutableStateOf(false) }
    var result by rememberSaveable { mutableStateOf("") }

    val efficiency = if (is75PercentEfficient) 0.75 else 0.7
    val efficiencyLabel = if (is75PercentEfficient) "75%" else "70%"

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "Gasolina ou Álcool?",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary)
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        // Box for inputs and result
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(vertical = 16.dp)
                .background(Color(0xFFEFEFEF))
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Icon and Title
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Gasolina ou Álcool?",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Inputs
                TextField(
                    value = alcoolPrice,
                    onValueChange = { alcoolPrice = it },
                    label = { Text("Preço do Álcool") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = gasolinaPrice,
                    onValueChange = { gasolinaPrice = it },
                    label = { Text("Preço da Gasolina") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                // Efficiency Switch
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "75%",
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp), // Adiciona margem à direita
                        fontSize = 16.sp,
                        textAlign = TextAlign.Right
                    )
                    Switch(
                        checked = is75PercentEfficient,
                        onCheckedChange = { is75PercentEfficient = it }
                    )
                }

                // Calculate Button
                Button(
                    onClick = {
                        val gasolina = gasolinaPrice.toFloatOrNull()
                        val alcool = alcoolPrice.toFloatOrNull()
                        if (gasolina != null && alcool != null) {
                            result = if (alcool / gasolina <= efficiency) {
                                "Abasteça com Álcool! ($efficiencyLabel eficiente)"
                            } else {
                                "Abasteça com Gasolina!"
                            }
                        } else {
                            result = "Insira preços válidos!"
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.6f)
                ) {
                    Text("Calcular")
                }

                // Result
                if (result.isNotEmpty()) {
                    Text(
                        text = result,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    GasolinaOuAlcoolAppTheme {
        MainScreen()
    }
}