package com.example.potel.ui.pets


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.potel.R

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//                PetInfoScreen()
//
//        }
//    }
//}

@Composable
fun PetInfoScreen() {
    var textState by remember { mutableStateOf(TextFieldValue("Pet names")) }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TextField for user input
        BasicTextField(
            value = textState,
            onValueChange = {
                if (it.text.length <= 20) {
                    textState = it
                }
            },

            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 20.sp, // 控制字體大小
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight() // 根據內容自動調整高度
                .padding(end = 32.dp),
            decorationBox = { innerTextField ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.Gray.copy(alpha = 0.2f), shape = MaterialTheme.shapes.medium)
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (textState.text.isEmpty()) {
                        Text("Pet names", style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray))
                    }
                    innerTextField()
                    // Magnifying glass icon
                    Image(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "Search Icon",
                        modifier = Modifier.size(30.dp).padding(start = 5.dp)
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(70.dp))

        // Pet Info List
        repeat(4) {
            PetItem(petName = "Max", petGender = "Male", petDescription = "Friendly and playful")
            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}

@Composable
fun PetItem(petName: String, petGender: String, petDescription: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Image
        Image(
            painter = painterResource(id = R.drawable.dog),
            contentDescription = "Dog Image",
            modifier = Modifier.size(100.dp).padding(end = 16.dp)
        )

        // Pet info box
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Pet Name: $petName")
            Text(text = "Gender: $petGender")
            Text(text = "Description: $petDescription")
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun PetInfoScreenPreview() {
//
//        PetInfoScreen()
//
//}

