package com.example.temperatureconverter
//This project was created using the empty compose activity option, instead of the empty activity option

import androidx.compose.foundation.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    MainActivityContent()
                }
            }
        }
    }
}

@Composable
fun Header(image: Int, description: String) {
    Image(
        painter = painterResource(image),
        contentDescription = description,
        modifier = Modifier.height(180 .dp).fillMaxWidth(),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun TemperatureText(celsius: Int) {
    val fahrenheit = (celsius.toDouble()*9/5) + 32
    Text("$celsius Celsius is $fahrenheit Fahrenheit")
}

@Composable
fun ConvertButton(clicked: () -> Unit) {
    Button(onClick = clicked) {
        Text("Convert")
    }
}

@Composable
fun EnterTemperature(temperature: String, changed: (String) -> Unit) {
    //a TextField is like an editText
    TextField(
        value = temperature,
        label = { Text("enter a temperature in Celsius") },
        onValueChange = changed,
        modifier = Modifier.fillMaxWidth() //this makes the TextField as wide as possible
    )
}

@Composable
fun MainActivityContent() {
    //doing mutableStateOf is kind of like using live data
    //Anytime this variable changes, the functions depending on its value will update as well
    val celsius = remember { mutableStateOf(0) }
    val newCelsius = remember { mutableStateOf("") }

    Column (modifier = Modifier.padding(16 .dp).fillMaxWidth()){
        Header(R.drawable.sunrise, "sunrise image")
        EnterTemperature(newCelsius.value) { newCelsius.value = it }
        //if you specify horizontalArrangement at the column level, all composables in the column will be effected
        //here, we add a row so that only the button will be centered
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            ConvertButton {
                //this converts newCelsius.value to an int, if it isn't null, and sets the value of celsius to the verified value of newCelsius
                newCelsius.value.toIntOrNull()?.let {
                    celsius.value = it
                }
            }
        }
        //since TemperatureText is using a mutableStateOf variable, it will change when celsius.value changes
        TemperatureText(celsius.value)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainActivity() {
    MaterialTheme {
        Surface {
            MainActivityContent()
        }
    }
}


