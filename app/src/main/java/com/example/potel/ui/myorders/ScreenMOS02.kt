package com.example.potel.ui.myorders

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.potel.R
import com.example.potel.ui.theme.PotelTheme

@Composable
fun ScreenMOS02(
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
            horizontalAlignment = Alignment.CenterHorizontally,
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
                    text = ">預約訂單",
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

            LazyVerticalGrid(
                columns = GridCells.Fixed(1), // 每列 1 行
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(20) { index ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                border = BorderStroke(width = 1.dp, Color.Black),
                                shape = RoundedCornerShape(10)
                            )
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .weight(1f)
//                        .border(border = BorderStroke(1.dp, Color.Black))

                        ){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween

                            ){
                                Text(text = "海景房",
                                    fontFamily = FontFamily.SansSerif,
                                    style = TextStyle(fontWeight = FontWeight(700),
                                        fontSize = 16.sp),
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "金額: 5000",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.End
                                )
                            }
                            Row { Text(text = "訂房時間") }
                        }
                        Column (modifier = Modifier.width(70.dp)){
                            Text(text = "毛毛")
                        }
                    }
                }
            }


//            // 一筆資料
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .border(
//                        border = BorderStroke(width = 1.dp, Color.Black),
//                        shape = RoundedCornerShape(10)
//                    )
//                    .padding(5.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(5.dp)
//                        .weight(1f)
////                        .border(border = BorderStroke(1.dp, Color.Black))
//
//                ){
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//
//                    ){
//                        Text(text = "海景房",
//                            fontFamily = FontFamily.SansSerif,
//                            style = TextStyle(fontWeight = FontWeight(700),
//                                fontSize = 16.sp),
//                            modifier = Modifier.weight(1f)
//                        )
//                        Text(
//                            text = "金額: 5000",
//                            modifier = Modifier.weight(1f),
//                            textAlign = TextAlign.End
//                        )
//                    }
//                    Row { Text(text = "訂房時間") }
//                }
//                Column (modifier = Modifier.width(70.dp)){
//                    Text(text = "毛毛")
//                }
//            }
//
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .border(
//                        border = BorderStroke(width = 1.dp, Color.Black),
//                        shape = RoundedCornerShape(10)
//                    )
//                    .padding(5.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(5.dp)
//                        .weight(1f)
////                        .border(border = BorderStroke(1.dp, Color.Black))
//
//                ){
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//
//                    ){
//                        Text(text = "海景房",
//                            fontFamily = FontFamily.SansSerif,
//                            style = TextStyle(fontWeight = FontWeight(700),
//                                fontSize = 16.sp),
//                            modifier = Modifier.weight(1f)
//                        )
//                        Text(
//                            text = "金額: 5000",
//                            modifier = Modifier.weight(1f),
//                            textAlign = TextAlign.End
//                        )
//                    }
//                    Row { Text(text = "訂房時間") }
//                }
//                Column (modifier = Modifier.width(70.dp)){
//                    Text(text = "毛毛")
//                }
//            }

        }



    }
}

@Preview(showBackground = true)
@Composable
fun ScreenMOS02Preview() {
    PotelTheme {
        ScreenMOS02(navController = rememberNavController())
    }
}