package com.example.potel.ui.shopping

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController


@Composable
fun TwoClassScreen(
    navController: NavHostController
) {
    val shoppingVM: ShopViewModel = viewModel(key = "shoppingVM")
    val context = LocalContext.current
    val preferences = context.getSharedPreferences("member", Context.MODE_PRIVATE)



    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ){


        Button(
            shape = RoundedCornerShape(20),
            border = BorderStroke(3.dp, Color.Yellow),
            modifier = Modifier
                .padding(0.dp)
                .width(200.dp)
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(20) )
                .background(Color.White)
                .height(185.dp),

            onClick = {
                navController.navigate(route = "${ShopScreens.Productlist.name}/D")
                      },
            colors = ButtonDefaults.buttonColors(
                // 設定按鈕容器顏色
                containerColor = Color.White,
                // 設定按鈕內容顏色
                contentColor = Color.Black

            )
        ) {
            Text(
                text = "狗勾天堂",
                fontSize = 36.sp, // 字型大小: 36
                fontWeight = FontWeight.ExtraBold, // 字體樣式: 粗體
                color = Color.Black // 字的顏色: 黑色
            )
        }
        Button(
            shape = RoundedCornerShape(20),
            border = BorderStroke(3.dp, Color.Yellow),
            modifier = Modifier
                .padding(0.dp)
                .width(200.dp)
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(20) )
                .background(Color.White)
                .height(185.dp),
            onClick = {
                navController.navigate("${ShopScreens.Productlist.name}/C")
            },
            colors = ButtonDefaults.buttonColors(
                // 設定按鈕容器顏色
                containerColor = Color.White,
                // 設定按鈕內容顏色
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "貓貓樂園",
                fontSize = 36.sp, // 字型大小: 36
                fontWeight = FontWeight.ExtraBold, // 字體樣式: 粗體
                color = Color.Black // 字的顏色: 黑色
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun TwoClassPreview() {
//    PotelTheme{
//        TwoClassScreen()
//    }
//}