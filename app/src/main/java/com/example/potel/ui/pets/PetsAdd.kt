package com.example.potel.ui.pets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            PetInfoForm()
//        }
//    }
//}

@Composable
fun PetInfoForm() {
    var ownerName by remember { mutableStateOf(TextFieldValue()) }
    var petName by remember { mutableStateOf(TextFieldValue()) }
    var petGender by remember { mutableStateOf(TextFieldValue()) }
    var contactInfo by remember { mutableStateOf(TextFieldValue()) }
    var petDiscribe by remember { mutableStateOf(TextFieldValue()) }
    var petImage by remember { mutableStateOf(TextFieldValue()) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(50.dp)
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Pet Information",
            style = TextStyle(fontSize = 30.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold),
            color = Color.Black
        )

        PetInfoTextField(
            label = "請輸入飼主名稱",
            textState = ownerName,
            onValueChange = { ownerName = it }
        )

        PetInfoTextField(
            label = "請輸入寵物名稱",
            textState = petName,
            onValueChange = { petName = it }
        )

        PetInfoTextField(
            label = "請輸入寵物性別",
            textState = petGender,
            onValueChange = { petGender = it }
        )

        PetInfoTextField(
            label = "請輸飼主聯絡方式",
            textState = contactInfo,
            onValueChange = { contactInfo = it }
        )

        PetInfoTextField(
            label = "請輸入寵物描述",
            textState = petDiscribe,
            onValueChange = { petDiscribe = it }
        )

        PetInfoTextField(
            label = "請上傳寵物影像",
            textState = petImage,
            onValueChange = { petImage = it }
        )

    }
}

@Composable
fun PetInfoTextField(label: String, textState: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    TextField(
        value = textState,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//
//        PetInfoForm()
//
//}
