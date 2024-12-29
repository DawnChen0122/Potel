package com.example.potel.ui.account

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Birthday(navController: NavHostController) {


    var inputYear by remember { mutableStateOf("") }
    var inputMonth by remember { mutableStateOf("") }
    var inputDay by remember { mutableStateOf("") }

    val yearRange = (1960..2024).map { it.toString() }
    val monthRange = (1..12).map { it.toString() }
    val dayRange = (1..31).map { it.toString() }


    var selectedYear by remember { mutableStateOf("") }
    var selectedMonth by remember { mutableStateOf("") }
    var selectedDay by remember { mutableStateOf("") }


    var expandedYear by remember { mutableStateOf(false) }
    var expandedMonth by remember { mutableStateOf(false) }
    var expandedDay by remember { mutableStateOf(false) }


    val filteredYears = yearRange.filter { it.startsWith(inputYear, true) }
    val filteredMonths = monthRange.filter { it.startsWith(inputMonth, true) }
    val filteredDays = dayRange.filter { it.startsWith(inputDay, true) }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
    {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp), // 水平間距
            verticalAlignment = Alignment.CenterVertically
        ) {
            ExposedDropdownMenuBox(
                expanded = expandedYear,
                onExpandedChange = { expandedYear = it },
            modifier = Modifier.weight(1f)
            ){
                TextField(
                    readOnly = false,
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true),
                    value = inputYear,
                    onValueChange = {
                        inputYear = it
                        expandedYear = true
                    },
                    singleLine = true,
                    label = { Text("年") },
                    trailingIcon = { TrailingIcon(expanded = expandedYear) }
                )
                ExposedDropdownMenu(
                    expanded = expandedYear,
                    onDismissRequest = { expandedYear = false }
                ) {
                    filteredYears.forEach { yearRange ->
                        DropdownMenuItem(
                            text = { Text(yearRange) },
                            onClick = {
                                selectedYear = yearRange
                                inputYear = yearRange
                                expandedYear = false
                            }
                        )
                    }
                }
            }


            ExposedDropdownMenuBox(
                expanded = expandedMonth,
                onExpandedChange = { expandedMonth = it },
                        modifier = Modifier.weight(1f) )
            {
                TextField(
                    readOnly = false,
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true),
                    value = inputMonth,
                    onValueChange = {
                        inputMonth = it
                        expandedMonth = true
                    },
                    singleLine = true,
                    label = { Text("月") },
                    trailingIcon = { TrailingIcon(expanded = expandedMonth) }
                )
                ExposedDropdownMenu(
                    expanded = expandedMonth,
                    onDismissRequest = { expandedMonth = false }
                ) {
                    filteredMonths.forEach { monthRange ->
                        DropdownMenuItem(
                            text = { Text(monthRange) },
                            onClick = {
                                selectedMonth = monthRange
                                inputMonth = monthRange
                                expandedMonth = false
                            }
                        )
                    }
                }
            }


            ExposedDropdownMenuBox(
                expanded = expandedDay,
                onExpandedChange = { expandedDay = it },
                modifier = Modifier.weight(1f) )
            {
                TextField(
                    readOnly = false,
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true),
                    value = inputDay,
                    onValueChange = {
                        inputDay = it
                        expandedDay = true
                    },
                    singleLine = true,
                    label = { Text("日") },
                    trailingIcon = { TrailingIcon(expanded = expandedDay) }
                )
                ExposedDropdownMenu(
                    expanded = expandedDay,
                    onDismissRequest = { expandedDay = false }
                ) {
                    filteredDays.forEach { dayRange ->
                        DropdownMenuItem(
                            text = { Text(dayRange) },
                            onClick = {
                                selectedDay = dayRange
                                inputDay = dayRange
                                expandedDay = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
    fun DefaultPreview9() {
        Birthday(navController = rememberNavController())
    }
