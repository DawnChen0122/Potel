package com.example.potel.ui.myorders

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.potel.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenMOS0302(
//    myOrdersViewModel: MyOrdersViewModel = viewModel(),
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .background(color = Color(0xFFD9D9D9), shape = RoundedCornerShape(size = 8.dp)),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "訂房訂單",
                    style = TextStyle(
                        fontSize = 24.sp,
                        lineHeight = 32.sp,
                        fontFamily = FontFamily(Font(R.font.dm_sans)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .padding(5.dp)
                )
                Text(
                    text = ">我要評分",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 32.sp,
                        fontFamily = FontFamily(Font(R.font.dm_sans)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .padding(5.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween // 讓內容分佈在兩端
            ) {
                Icon(
                    imageVector = Icons.Filled.ThumbUp,
                    contentDescription = "糟",
                    modifier = Modifier.graphicsLayer(
                        rotationX = 180f // 將圖標上下顛倒
                    )
                        .weight(0.5f)
                )

                Row {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "實心"
                    )
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "實心"
                    )
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "實心"
                    )
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "實心"
                    )
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "實心"
                    )
                    Icon(
//                    painter = painterResource(id = R.drawable.room),
                        imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription = "空心"
                    )
                }
                Icon(
                    imageVector = Icons.Filled.ThumbUp,
                    contentDescription = "讚",
                    modifier = Modifier.weight(0.5f)
                )

            }

            Text(
                text = "您的評價:",
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 32.sp,
                    fontFamily = FontFamily(Font(R.font.dm_sans)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .padding(8.dp)
            )

            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text(text = "請把您的想法寫在這, 最多可寫300個字") },
                maxLines = 10,
                modifier = Modifier.weight(1f)
            )

            OutlinedButton(
                onClick = {

                },
                shape = RoundedCornerShape(20),
                border = BorderStroke(width = 1.dp, color = Color.Black),
                contentPadding = PaddingValues(5.dp),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "我要評分",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.dm_sans)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF000000),
                    ),
                )
            }

        }
    }
}