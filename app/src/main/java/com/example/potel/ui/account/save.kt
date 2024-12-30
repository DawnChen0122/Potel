//import android.content.Context
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            BookshopDemo_AndroidTheme {
//                Main()
//            }
//        }
//    }
//}
//
//@Composable
//fun Main() {
//    val context = LocalContext.current
//    val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
//    // 取出當初儲存資料，如果沒有儲存則回傳空字串
//    var uid by remember { mutableStateOf(preferences.getString("uid", "")!!) }
//    var showRegister by remember { mutableStateOf(false) }
//
//    if (uid.isEmpty()) {
//        if (!showRegister) {
//            // 顯示登入頁面
//            UserLoginScreen(
//                // 登入成功，將uid存檔
//                onLoginSuccess = {
//                    uid = it
//                    preferences.edit().putString("uid", uid).apply()
//                },
//                // 切換到註冊頁面
//                onRegisterClick = { showRegister = true })
//        } else {
//            // 顯示註冊頁面
//            UserRegisterMainScreen(
//                // 註冊成功等於登入成功，一樣將uid存檔
//                onRegisterSuccess = {
//                    uid = it
//                    preferences.edit().putString("uid", uid).apply()
//                    // 註冊成功後如果登出，應該切換到登入畫面；
//                    // 如果showRegister = true，會切換到註冊畫面
//                    showRegister = false
//                },
//                // 返回登入頁面
//                onBackToLogin = { showRegister = false },
//            )
//        }
//    } else {
//        // 登入成功後進入書的展示流程
//        BookMainScreen(
//            // 點擊Logout按鈕會登出
//            onLogout = { uid = "" })
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    BookshopDemo_AndroidTheme {
//        Main()
//    }
//}