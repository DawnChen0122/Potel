package com.example.potel.ui.petsfile
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ComponentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.potel.ui.theme.PotelTheme
import kotlinx.coroutines.launch

@SuppressLint("RestrictedApi")
class PetsFileCatsActivity : ComponentActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PotelTheme  {
                ScreensPetsFileCats(navController = rememberNavController())
            }
        }
    }

    private fun setContent(function: @Composable () -> Unit) {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreensPetsFileCats(petsFileViewModel: PetsFileCatsViewModel = viewModel(),
                        navController: NavHostController
) {
    var inputText by remember { mutableStateOf("") }
    // 從StateFlow取得並呈現最新的值
    val cats by petsFileViewModel.catsState.collectAsState()
    // 設定內容向上捲動時，TopAppBar自動收起來；呼叫pinnedScrollBehavior()則不會收起來
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showMore by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    // 暫存被點選編輯的書籍
    var editCats by remember { mutableStateOf(PetsCat()) }

    Scaffold(
        // 設定則可追蹤捲動狀態，藉此調整畫面(例如內容向上捲動時，TopAppBar自動收起來)
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = inputText,
                        onValueChange = { inputText = it },
                        // 在此placeholder較label(會將提示文字上移)適合
                        placeholder = { Text(text = "cats name") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search, contentDescription = "search"
                            )
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Default.Clear,
                                contentDescription = "clear",
                                modifier = Modifier.clickable {
                                    inputText = ""
                                })
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp)
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomAppBar(actions = {
                // 新增按鈕
                IconButton(onClick = {
                    showAddDialog = true
                }) {
                    Icon(Icons.Filled.Add, contentDescription = "add")
                }

                // 異動按鈕(顯示/隱藏末端的編輯、刪除按鈕)
                IconButton(onClick = {
                    showMore = !showMore
                }) {
                    Icon(
                        Icons.Filled.MoreVert,
                        contentDescription = "edit and delete",
                    )
                }
            })
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }

    ) { innerPadding ->
        // 一定要套用innerPadding，否則內容無法跟TopAppBar對齊
        CatsLists(cats.filter { it.catName.contains(inputText, true) },
            innerPadding,
            // 項目被點擊時執行
            onItemClick = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        "${it.catName}, $${it.catbreed}",
                        withDismissAction = true
                    )
                }
            },
            showMore,
            // 編輯按鈕被點擊時執行
            onEditClick = {
                editCats = it
                showEditDialog = true
            },
            // 刪除按鈕被點擊時執行
            onDeleteClick = {
                // 將欲刪除的書從list移除
                petsFileViewModel.removeItem(it)
                // 將刪除的書名以Snackbar顯示
                scope.launch {
                    snackbarHostState.showSnackbar(
                        "${it.catName} deleted", withDismissAction = true
                    )
                }
            })
        if (showAddDialog) {
            // 顯示新增對話視窗
            AddDialogCats (
                // 取消時欲執行內容
                onDismiss = {
                    showAddDialog = false
                })
            // onAdd: 確定新增時欲執行內容
            {
                // 將欲新增的書加入到list
                petsFileViewModel.addCat(it)
                showAddDialog = false
                // 新增成功後該書會被加到最後一筆，使用者可能看不到該書資訊；
                // 將查詢文字換成新增的書名，可立即顯示該書資訊
                inputText = it.catName
                // 將新增的書名以Snackbar顯示
                scope.launch {
                    snackbarHostState.showSnackbar(
                        "${it.catName} added", withDismissAction = true
                    )
                }
            }
        }
        if (showEditDialog) {
            // 顯示編輯對話視窗
            EditDialog(
                editCats,
                // 取消時欲執行內容
                onDismiss = {
                    showEditDialog = false
                })
            // onEdit: 確定編輯時欲執行內容
            {
                showEditDialog = false
                // 編輯成功後將查詢文字換成編輯的書名，可立即顯示該書資訊
                inputText = it.catName
                // 將修改好的書名以Snackbar顯示
                scope.launch {
                    snackbarHostState.showSnackbar(
                        "${it.catName} updated", withDismissAction = true
                    )
                }
            }
        }
    }
}

/**
 * 列表內容
 * @param books 欲顯示的書籍清單
 * @param innerPadding 元件要套用innerPadding，否則內容無法跟TopAppBar對齊
 * @param onEditClick 點擊列表項目時所需呼叫的函式
 */
@Composable
fun CatsLists(
    pets: List<PetsCat>,
    innerPadding: PaddingValues,
    onItemClick: (PetsCat) -> Unit,
    showMore: Boolean,
    onEditClick: (PetsCat) -> Unit,
    onDeleteClick: (PetsCat) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {

        items(pets) { cats ->
            // 用來建立Lists內容物
            ListItem(
                // 讓項目可被點擊，並設定點擊時欲執行內容
                modifier = Modifier.clickable {
                    onItemClick(cats)
                },
                headlineContent = { Text(cats.catName) },
                supportingContent = { Text(cats.catbreed) },
                leadingContent = {
                    AsyncImage(
                        modifier = Modifier.size(50.dp),
                        model = "http://10.0.2.2:8080/PotelServer/api/image?imageid=${cats.catImages}",
                        contentDescription = ""
                    )
                },
                trailingContent = {
                    if (showMore) {
                        Row {
                            // 編輯按鈕
                            IconButton(onClick = {
                                onEditClick(cats)
                            }) {
                                Icon(Icons.Filled.Edit, contentDescription = "edit")
                            }
                            // 刪除按鈕
                            IconButton(onClick = {
                                onDeleteClick(cats)
                            }) {
                                Icon(Icons.Filled.Delete, contentDescription = "delete")
                            }
                        }
                    }
                }
            )
            HorizontalDivider()
        }
    }
}

@Composable
fun AddDialogCats(
    onDismiss: () -> Unit,
    onAdd: (PetsCat) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp),
            // 形狀設定為圓角矩形
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Add Cats",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Cats name") }
                )
                TextField(
                    value = breed,
                    onValueChange = { breed = it },
                    label = { Text(text = "Cats breed") }
                )
                TextField(
                    value = gender,
                    onValueChange = { gender = it },
                    label = { Text(text = "Gender") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        val newCats = PetsCat(
                            name,1, breed, gender,
                            // 隨意給個封面圖
                            "1"
                        )
                        onAdd(newCats)
                    }) {
                        Text("Add")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = onDismiss) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}

@Composable
fun EditDialog(
    cats: PetsCat,
    onDismiss: () -> Unit,
    onEdit: (PetsCat) -> Unit
) {
    var name by remember { mutableStateOf(cats.catName) }
    var breed by remember { mutableStateOf(cats.catbreed) }
    var gender by remember { mutableStateOf(cats.catgender) }
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp),
            // 形狀設定為圓角矩形
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Edit Cats",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )
                Text(text = "Cats: ${cats.catName}, ${cats.catbreed}, ${cats.catgender}")
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Cats name") }
                )
                TextField(
                    value = breed,
                    onValueChange = { breed = it },
                    label = { Text(text = "Cats breed") }
                )
                TextField(
                    value = gender,
                    onValueChange = { gender = it },
                    label = { Text(text = "Cats gender") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        // 就將原本書內容替換成使用者輸入的新內容，原始books內容也會更新
                        cats.catName = name
                        cats.catbreed = breed
                        cats.catgender = gender
                        onEdit(cats)
                    }) {
                        Text("Update")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = onDismiss) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ScreensPetsFileCatsPreview() {
    PotelTheme  {
        ScreensPetsFileCats(navController = rememberNavController())
    }
}