package com.example.potel.ui.petsfile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.potel.R
import com.example.potel.ui.theme.PotelTheme

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//                MainScreen()
//            }
//        }
//    }

@Composable
fun ScreenPetsFileFirst( navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF0E68C)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // 空白50個字元
        Spacer(modifier = Modifier.height(50.dp))

        // 顯示標題 "Potel"
        Text(
            text = "Potel",
            style = TextStyle(fontSize = 50.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold),
            color = Color.Black
        )

        // 空白200個字元
        Spacer(modifier = Modifier.height(200.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.user), // 替換為 dog 圖片資源ID
                contentDescription = "User",
                modifier = Modifier.size(100.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { /* 按下Add Dog的動作 */ }) {
                Text(text = "Add user")
            }
        }
            Spacer(modifier = Modifier.height(100.dp))

        // 顯示圖片A (dog)
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.dog), // 替換為 dog 圖片資源ID
                contentDescription = "Dog",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(30.dp)) // 圖片間隙
            // 顯示圖片B (cat)
            Image(
                painter = painterResource(id = R.drawable.cat), // 替換為 cat 圖片資源ID
                contentDescription = "Cat",
                modifier = Modifier.size(100.dp)
            )
        }

        // 為每個圖片下方顯示按鈕
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { /* 按下Add Dog的動作 */ }) {
                Text(text = "Add dog")
            }
            Spacer(modifier = Modifier.width(30.dp))
            Button(onClick = { /* 按下Add Cat的動作 */ }) {
                Text(text = "Add cat")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenPetsFileFirstPreview() {
    PotelTheme  {
        ScreenPetsFileFirst(rememberNavController())
    }
}

