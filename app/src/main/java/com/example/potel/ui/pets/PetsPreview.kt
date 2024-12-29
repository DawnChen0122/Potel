package com.example.potel.ui.pets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import com.example.potel.R


data class PetInfo(val name: String, val gender: String, val description: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetApp()
        }
    }
}

@Composable
fun PetApp() {
    var inputText by remember { mutableStateOf(TextFieldValue("Pet names")) }
    val pets = listOf(
        PetInfo("Max", "Male", "Friendly and energetic"),
        PetInfo("Bella", "Female", "Loves to play"),
        PetInfo("Charlie", "Male", "Calm and loyal"),
        PetInfo("Daisy", "Female", "Loves cuddles"),
        PetInfo("Luna", "Female", "Curious and adventurous")
    )
    Spacer(modifier = Modifier.height(10.dp))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TextField with search icon
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = inputText,
                onValueChange = { inputText = it },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { /* Handle done action if needed */ }),
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )
            IconButton(onClick = { /* Handle search action */ }) {
                Icon(painter = painterResource(id = R.drawable.search), contentDescription = "Search")
            }
        }

        Spacer(modifier = Modifier.height(70.dp))

        // List of pet data
        pets.forEach { pet ->
            PetItem(pet = pet)
            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}

@Composable
fun PetItem(pet: PetInfo) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Pet image
        val petImage: Painter = painterResource(id = R.drawable.dog)
        Image(painter = petImage, contentDescription = "Pet Image", modifier = Modifier.size(100.dp))

        Spacer(modifier = Modifier.width(16.dp))

        // Pet information
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Text("Name: ${pet.name}")
            Text("Gender: ${pet.gender}")
            Text("Description: ${pet.description}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PetApp()
}
