package com.example.potel.ui.forumZone

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.potel.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentEditScreen(
    navController: NavHostController,
) {
    val tag = "CommentEditScreen"
    val backStackEntry = navController.getBackStackEntry(ForumScreens.ForumScreen.name)
    val forumVM: ForumVM = viewModel(backStackEntry)
    val comment = forumVM.commentSelectedState.collectAsState()
    Log.d(tag, "CommentEditScreen - Comment: ${comment.value}")
    var content by remember { mutableStateOf(comment.value.content)}
    val scope = rememberCoroutineScope()
    val hostState = remember { SnackbarHostState() }
    var showDialog by remember { mutableStateOf(false)}

    Scaffold(
        topBar = {
           TopAppBar(
               title = { Text("") },
               navigationIcon ={
                   IconButton(onClick = {
                       if (content.isNotEmpty()) showDialog = true
                       else navController.navigateUp()
                   }) {
                       Icon(
                           imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                           contentDescription = stringResource(R.string.back_button),
                           tint = Color.LightGray
                       )
                   }
               },
               colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                   containerColor = colorResource(R.color.forum)
               ),
               actions = {
                   Button(
                       onClick = {
                           if (content.isEmpty()) {
                               scope.launch { hostState.showSnackbar("內容必須填寫！", withDismissAction = true) }
                           }else{
                               val updateComment =Comment(
                                   commentId = comment.value.commentId,
                                   content = content
                               )
                               forumVM.updateComment(updateComment)
                               forumVM.setPostSuccessMessage("編輯完成")
                               navController.popBackStack()
                           }
                       }, shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(
                           containerColor = colorResource(R.color.forumButton),
                           contentColor = Color.Black)
                   ) {
                           Text("保存變更", fontSize = 15.sp)
                   }
                   Spacer(Modifier.width(8.dp))
               }
           )
        },
        snackbarHost = {
            SnackbarHost(hostState = hostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    dismissActionContentColor = Color.Black
                )
            }
        }
    ){ paddingValues ->
        Column(Modifier.padding( top =  paddingValues.calculateTopPadding())
                        .background(colorResource(R.color.forum))
                        .fillMaxSize()
        ) {
            HorizontalDivider(
                Modifier.padding(start = 10.dp, end = 10.dp),
                thickness = 1.dp,
                color = Color.DarkGray
            )
            Column(modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Spacer(Modifier.height(35.dp))
                OutlinedTextField(
                    value = content,
                    onValueChange = { if (it.length <= 150) content = it },
                    placeholder = { Text("輸入留言內容", color = Color.LightGray)},
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = colorResource(R.color.forumButton)
                    ),
                    minLines = 1
                )
            }
        }
    }
    if (showDialog) {
        BasicAlertDialog(
            onDismissRequest = { showDialog = false },
            content = {
                Column(
                    modifier = Modifier
                        .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                        .height(240.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(50.dp))
                    Text("捨棄更改")
                    Text("確定不保存變更？")
                    Spacer(Modifier.height(50.dp))
                    Row(
                        modifier = Modifier.height(50.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = { showDialog = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Black
                            ),
                            border = BorderStroke(1.dp, Color.DarkGray),
                            modifier = Modifier.width(110.dp)
                        ) {
                            Text("取消")
                        }
                        Spacer(Modifier.width(20.dp))
                        Button(
                            onClick = {
                                showDialog = false
                                forumVM.setPostSuccessMessage("編輯完成！")
                                navController.navigate(ForumScreens.PostScreen.name)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Red
                            ),
                            border = BorderStroke(1.dp, Color.DarkGray),
                            modifier = Modifier.width(110.dp)
                        ) {
                            Text("捨棄更改")
                        }
                    }
                }
            }
        )
    }
}


