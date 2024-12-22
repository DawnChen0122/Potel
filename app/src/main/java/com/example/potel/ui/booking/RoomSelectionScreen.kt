package com.example.potel.ui.booking

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RoomSelectionScreen(onSelectRoom: (Room) -> Unit) {
    var selectedPetType by remember { mutableStateOf("Dog") }
    var dogWeight by remember { mutableStateOf(0) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("選擇寵物類型", style = MaterialTheme.typography.h5)

        Row {
            RadioButton(selected = selectedPetType == "Dog", onClick = { selectedPetType = "Dog" })
            Text("狗")
            RadioButton(selected = selectedPetType == "Cat", onClick = { selectedPetType = "Cat" })
            Text("貓")
        }

        if (selectedPetType == "Dog") {
            TextField(value = dogWeight.toString(), onValueChange = { dogWeight = it.toIntOrNull() ?: 0 }, label = { Text("輸入狗的重量") })
            // 顯示根據重量篩選的房型
            DogRoomList(dogWeight, onSelectRoom)
        } else {
            // 顯示貓的房型
            CatRoomList(onSelectRoom)
        }
    }
}

@Composable
fun DogRoomList(weight: Int, onSelectRoom: (Room) -> Unit) {
    val rooms = getDogRoomsByWeight(weight)
    LazyColumn {
        items(rooms) { room ->
            RoomItem(room, onSelectRoom)
        }
    }
}

@Composable
fun CatRoomList(onSelectRoom: (Room) -> Unit) {
    val rooms = getCatRooms()
    LazyColumn {
        items(rooms) { room ->
            RoomItem(room, onSelectRoom)
        }
    }
}

@Composable
fun RoomItem(room: Room, onSelectRoom: (Room) -> Unit) {
    Card(modifier = Modifier.padding(8.dp).clickable { onSelectRoom(room) }) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(room.name, style = MaterialTheme.typography.h6)
            Text("價格: ${room.price}元")
        }
    }
}

