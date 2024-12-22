package com.example.potel.ui.booking

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

/**
 * todo 2-2 將首頁的畫面獨立出來
 * 將每個頁面拆分成三個區塊
 * (1) BookingRoute 使用 ViewModel 的資料，此為 NavigationController 導向的起始點
 * (2) BookingScreen 實際畫面，將參數抽出來，方便 Preview
 * (3) PreviewBookingScreen 預覽畫面（不透過 ViewModel 預覽畫面）
 * */

@Composable
fun BookingRoute(
    homeViewModel: HomeViewModel = viewModel(),
    navController: NavHostController
) {
}

@Preview
@Composable
fun PreviewBookingScreen() {
    BookingScreen()
}

@Composable
fun BookingRoute(navController: NavHostController){
    var currentScreen by remember { mutableSetOf(0) }
    // 0:房型選擇，1:日期選擇，2:確認，3:付款，4:成功

    when(currentScreen){
        0 -> RoomSelectionScreen(onSelectRoom = { room ->
            currentScreen = 1 //前往日期選擇畫面
            // 儲存選擇的房型以便後續使用（如使用 ViewModel 或其他方式）
        })
        1 -> DateSelectionScreen(onDateSelected = { petName, date ->
            currentScreen = 2 //前往確認畫面
            // 儲存寵物名稱和日期以便後續使用（如使用 ViewModel 或其他方式）
        })
        2 -> BookingConfirmationScreen( room = /* 獲取儲存的房型 */, petName = /* 獲取儲存的寵物名稱 */, date = /* 獲取儲存的日期 */) {
            currentScreen = 3 // 前往付款畫面
        }
        3 -> PaymentScreen(room = /* 獲取儲存的房型 */, petName = /* 獲取儲存的寵物名稱 */, date = /* 獲取儲存的日期 */) {
            currentScreen = 4 // 前往成功畫面或處理付款邏輯後轉換到成功畫面
        }
        4 -> BookingSuccessScreen()
    }
}

// 房型選擇頁面 (RoomSelectionScreen.kt)
@Composable
fun RoomSelectionScreen(onSelectRoom: (Room) -> Unit) {
    val rooms = getAvailableRooms() // 假設這是一個函數，返回可用的房型列表

    Column(modifier = Modifier.padding(16.dp)) {
        Text("選擇房型", style = MaterialTheme.typography.h5)

        LazyColumn {
            items(rooms) { room ->
                RoomItem(room, onSelectRoom)
            }
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

// 日期選擇頁面 (DateSelectionScreen.kt)
@Composable
fun DateSelectionScreen(onDateSelected: (String, String) -> Unit) {
    var selectedDate by remember { mutableStateOf("") }
    var petName by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("選擇住宿日期", style = MaterialTheme.typography.h5)

        TextField(value = petName, onValueChange = { petName = it }, label = { Text("輸入寵物名稱") })
        TextField(value = selectedDate, onValueChange = { selectedDate = it }, label = { Text("輸入日期") })

        Button(onClick = { onDateSelected(petName, selectedDate) }) {
            Text("確認日期")
        }
    }
}

// 訂單確認頁面 (BookingConfirmationScreen.kt)
@Composable
fun BookingConfirmationScreen(room: Room, petName: String, date: String, onConfirm: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("確認住房資訊", style = MaterialTheme.typography.h5)
        Text("房型: ${room.name}")
        Text("價格: ${room.price}元")
        Text("住宿日期: $date")
        Text("寵物名稱: $petName")

        Button(onClick = onConfirm) {
            Text("前往付款")
        }
    }
}

// 付款頁面 (PaymentScreen.kt)
@Composable
fun PaymentScreen(room: Room, petName: String, date: String) {
    var cardNumber by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("付款", style = MaterialTheme.typography.h5)

        TextField(value = cardNumber, onValueChange = { cardNumber = it }, label = { Text("信用卡號碼") })

        // 顯示訂單摘要
        Text("房型: ${room.name}")
        Text("住宿日期: $date")
        Text("寵物名稱: $petName")

        Button(onClick = {
            // TODO: 處理付款邏輯，然後轉換到成功畫面或顯示錯誤訊息。
            println("Processing payment for ${room.name} for pet $petName on $date")
            // 假設付款成功，轉換到成功畫面。
            // currentScreen變更為4或導航到成功畫面。
        }) {
            Text("確認付款")
        }
    }
}

// 預約成功頁面 (BookingSuccessScreen.kt)
@Composable
fun BookingSuccessScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("預約成功！", style = MaterialTheme.typography.h5)
        Text("謝謝您的預約，期待您的到來！")

        Button(onClick = { /* 返回主頁或其他操作 */ }) {
            Text("返回主頁")
        }
    }
}


