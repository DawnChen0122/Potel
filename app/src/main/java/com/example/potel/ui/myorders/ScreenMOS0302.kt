package com.example.potel.ui.myorders

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.potel.R
import com.example.potel.ui.theme.TipColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenMOS0302(
    navController: NavHostController,
    orderid: String
) {
    val tag = "ScreenMOS0302"
    val backStackEntry = navController.getBackStackEntry(MyOrdersScreens.MOS01.name)
    val myOrdersViewModel: MyOrdersViewModel = viewModel(backStackEntry, key = "myOrdersVM")

    val coroutineScope = rememberCoroutineScope()
    var order by remember { mutableStateOf<Order?>(null) }
    var score by remember { mutableIntStateOf(0) }
    var comment by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            order = myOrdersViewModel.getOrder(orderid.toInt())
            myOrdersViewModel.setOrder(order)
            score = order?.score?:0
            comment = order?.comment?:""
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(color = TipColor.deep_brown),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
//                .background(color = Color(0xFFD9D9D9), shape = RoundedCornerShape(size = 8.dp)),
                .background(
                    color = TipColor.light_brown,
                    shape = RoundedCornerShape(size = 8.dp)
                ),
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
//                        color = Color(0xFF000000),
                        color = TipColor.deep_brown,
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
//                        color = Color(0xFF000000),
                        color = TipColor.deep_brown,
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
                    tint = TipColor.deep_brown,
                    contentDescription = "糟",
                    modifier = Modifier
                        .graphicsLayer(
                            rotationX = 180f // 將圖標上下顛倒
                        )
                        .weight(0.5f)
                        .clickable {
                            score--
                        }
                )

                Row {
                    Icon(
                        imageVector = if(score>=1) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "實心",
                        modifier = Modifier.clickable {
                            score = 1
                        }
                    )
                    Icon(
                        imageVector = if(score>=2) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "實心",
                        modifier = Modifier.clickable {
                            score = 2
                        }
                    )
                    Icon(
                        imageVector = if(score>=3) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "實心",
                        modifier = Modifier.clickable {
                            score = 3
                        }
                    )
                    Icon(
                        imageVector = if(score>=4) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "實心",
                        modifier = Modifier.clickable {
                            score = 4
                        }
                    )
                    Icon(
                        imageVector = if(score>=5) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "實心",
                        modifier = Modifier.clickable {
                            score = 5
                        }
                    )
                }
                Icon(
                    imageVector = Icons.Filled.ThumbUp,
                    contentDescription = "讚",
                    modifier = Modifier
                        .weight(0.5f)
                        .clickable {
                            score++
                        }
                )

            }

            Text(
                text = "您的評價:",
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 32.sp,
                    fontFamily = FontFamily(Font(R.font.dm_sans)),
                    fontWeight = FontWeight(700),
//                        color = Color(0xFF000000),
                    color = TipColor.deep_brown,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .padding(8.dp)
            )

            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it},
                placeholder = { Text(text = "請把您的想法寫在這, 最多可寫300個字") },
                maxLines = 10,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                keyboardOptions = KeyboardOptions(
                    showKeyboardOnFocus = true,
                    keyboardType = KeyboardType.Unspecified
                )
            )

            OutlinedButton(
                onClick = {
                    order?.score = score
                    order?.comment = comment
                    myOrdersViewModel.setOrder(order)
                    navController.navigate(MyOrdersScreens.MOS0303.name)
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
//                        color = Color(0xFF000000),
                        color = TipColor.deep_brown,
                    ),
                )
            }

        }
    }
}