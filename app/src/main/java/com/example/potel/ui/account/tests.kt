//package com.example.potel.ui.account
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Lock
//import androidx.compose.material3.*
//import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.rememberNavController
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun Signupt(viewModel:AccountViewModel = viewModel(), navController: NavHostController) {
////    val items = viewModel.items.collectAsState()
//
//    var inputYear by remember { mutableStateOf("") }
//    val yearRange = (1924..2025).map { it.toString() }
//    var selectedYear by remember { mutableStateOf("") }
//    var expandedYear by remember { mutableStateOf(false) }
//    val filteredYears = yearRange.filter { it.startsWith(inputYear, true) }
//
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "會員註冊",
//            fontSize = 30.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Blue
//        )
//
//
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 10.dp)
//                .background(Color.White, RoundedCornerShape(8.dp))
//        ) {
//            Text(
//                text = "請選擇出生年月日",
//                modifier = Modifier.padding(10.dp)
//            )
//        }
//
//        Row(
//            horizontalArrangement = Arrangement.spacedBy(8.dp),
//            verticalAlignment = Alignment.Top
//        ) {
//            ExposedDropdownMenuBox(
//                expanded = expandedYear,
//                onExpandedChange = { expandedYear = it },
//                modifier = Modifier.weight(1f)
//            ) {
//                TextField(
//                    readOnly = true,
//                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true),
//                    value = inputYear,
//                    onValueChange = {
//                        inputYear = it
//                        expandedYear = true
//                    },
//                    singleLine = true,
//                    label = { Text("年") },
//                    trailingIcon = { TrailingIcon(expanded = expandedYear) }
//                )
//                ExposedDropdownMenu(
//                    expanded = expandedYear,
//                    onDismissRequest = { expandedYear = false }
//                ) {
//                    filteredYears.forEach { yearRange ->
//                        DropdownMenuItem(
//                            text = { Text(yearRange) },
//                            onClick = {
//                                selectedYear = yearRange
//                                inputYear = yearRange
//                                expandedYear = false
//                            }
//                        )
//                    }
//                }
//            }
//        }
//
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview11() {
//    Signupt(navController = rememberNavController())
//}
