package com.example.potel.ui.home


import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.potel.ui.booking.BookingScreens
import com.example.potel.ui.forumZone.ForumScreens
import com.example.potel.ui.petsfile.PetsFileScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRoute(
    navController: NavHostController = rememberNavController()
) {


    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .border(width = 5.dp, color = Color(0xFF000000))
                .padding(5.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(0xFFF7E3A6))
                .padding(top = 12.dp, bottom = 12.dp)
                .padding(innerPadding)
        ) {
            Text(
                text = "OOO先生/小姐您好",
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 46.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .width(176.dp)
                    .height(46.dp)
            )

            Text(
                text = "入住須知",
                style = TextStyle(
                    fontSize = 27.sp,
                    lineHeight = 49.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .width(246.dp)
                    .height(49.dp)
            )


            Text(
                text = "1.請提供健康證明及疫苗紀錄。\n" +
                        "2.請遵守旅館規定，保持環境整潔。\n" +
                        "3.如有突發狀況，請立即聯絡我們。",
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 49.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )




            Text(
                text = "最新消息",
                style = TextStyle(
                    fontSize = 27.sp,
                    lineHeight = 49.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .width(246.dp)
                    .height(49.dp)
            )



            Text(
                text = "1.喵喵喵喵喵喵喵喵。\n" +
                        "2.旺旺旺旺旺旺旺旺。\n" +
                        "3.產品都優質美味歡迎購買。",
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 49.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(21.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {

                Text(
                    text = "顯示房客評價",
                    style = TextStyle(
                        fontSize = 27.sp,
                        lineHeight = 32.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .width(246.dp)
                        .height(49.dp)
                )

                Button(
                    shape = RoundedCornerShape(20),
                    onClick = {
                        navController.navigate(route = BookingScreens.Booking.name)
                    },
                    border = BorderStroke(1.dp, Color.Black),
                    colors = ButtonDefaults.outlinedButtonColors(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                )
                {
                    Text(
                        text = "房型介紹",
                        style = TextStyle(
                            fontSize = 26.sp,
                            lineHeight = 49.sp,
                            fontWeight = FontWeight(700),
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier
                            .width(246.dp)
                            .height(49.dp)
                    )
                }
            }
                Row(
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Button(
                        shape = RoundedCornerShape(20),
                        onClick = {
                            navController.navigate(route = ForumScreens.ForumScreen.name)
                        },
                        border = BorderStroke(1.dp, Color.Black),
                        colors = ButtonDefaults.outlinedButtonColors(),
                        modifier = Modifier

                    )
                    {
                        Text(
                            text = "討論區",
                            style = TextStyle(
                                fontSize = 27.sp,
                                lineHeight = 32.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF000000),
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier
                                .width(100.dp)
                                .height(45.dp)
                        )
                    }

                    Button(
                        shape = RoundedCornerShape(20),
                        onClick = {
                            navController.navigate(route = PetsFileScreens.PetsFileFirst.name)
                        },
                        border = BorderStroke(1.dp, Color.Black),
                        colors = ButtonDefaults.outlinedButtonColors(),
                        modifier = Modifier

                    )
                    {
                        Text(
                            text = "寵物/會員資料",
                            style = TextStyle(
                                fontSize = 27.sp,
                                lineHeight = 32.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF000000),
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier
                                .width(180.dp)
                                .height(45.dp)
                        )
                    }
                }
            }
        }
    }



@Preview(showBackground = true)
@Composable
fun DefaultPreview10() {
    HomeRoute(navController = rememberNavController())
}
