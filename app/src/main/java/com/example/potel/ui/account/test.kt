//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Email
//import androidx.compose.material.icons.filled.Lock
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.potel.ui.account.TipHomeItemUiState
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//
//private val _password = MutableStateFlow<List<TipHomeItemUiState>>(listOf())
//val password: StateFlow<List<TipHomeItemUiState>> = _password.asStateFlow()
//var passwordError by mutableStateOf(false)
//fun onPasswordChanged(password: String){
//    val passwordRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
//    passwordError = !password.matches(passwordRegex)
//    _password.value = listOf(TipHomeItemUiState(title = password, imageVector = Icons.Filled.Email))
//}
//
//
//
//private val _checkpassword = MutableStateFlow<List<TipHomeItemUiState>>(listOf())
//val checkpassword: StateFlow<List<TipHomeItemUiState>> = _password.asStateFlow()
//var checkpasswordError by mutableStateOf(false)
//fun onCheckPasswordChanged(checkpassword: String){
//    val checkpasswordRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
//    checkpasswordError = !checkpassword.matches(checkpasswordRegex)
//    _checkpassword.value = listOf(TipHomeItemUiState(title = checkpassword, imageVector = Icons.Filled.Email))
//}
//
//
//val password = viewModel.password.collectAsState()
//var passwordVisible by remember { mutableStateOf(false) }
//
//
//var checkpassword = viewModel.checkpassword.collectAsState()
//var checkpasswordVisible  by remember { mutableStateOf(false) }
//
//
//
//
//
//OutlinedTextField(
//value = password.value.firstOrNull()?.title ?: "",
//onValueChange = viewModel::onPasswordChanged,
////            onValueChange = {
////                password.value = it
////                passwordError = { it.length < 6 || it.length > 20 },
//label = { Text(text = "密碼") },
//leadingIcon = {
//    Icon(
//        imageVector = Icons.Default.Lock,
//        contentDescription = "密碼"
//    )
//},
//trailingIcon = {
//    Text(
//        text = if (passwordVisible) "隱藏" else "顯示",
//        modifier = Modifier.clickable {
//            passwordVisible = !passwordVisible
//        }
//    )
//},
//isError = viewModel.passwordError,
//shape = RoundedCornerShape(8.dp),
//visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//singleLine = true,
//colors = TextFieldDefaults.colors(
//focusedIndicatorColor = Color.Blue,
//unfocusedIndicatorColor = Color.Gray,
//),
//modifier = Modifier
//.fillMaxWidth()
//.padding(top = 16.dp)
//)
//if (viewModel.passwordError) {
//    Text(
//        text = "密碼需在6 至 20 字符內",
//        color = Color.Red,
//        fontSize = 12.sp,
//        modifier = Modifier.padding(start = 16.dp)
//    )
//}
//
//
//OutlinedTextField(
//value = checkpassword.value.firstOrNull()?.title ?: "",
//onValueChange = viewModel::onCheckPasswordChanged,
//label = { Text(text = "再次確認密碼") },
//leadingIcon = {
//    Icon(
//        imageVector = Icons.Default.Lock,
//        contentDescription = "再次確認密碼"
//    )
//},
//trailingIcon = {
//    Text(
//        text = if (checkpasswordVisible) "隱藏" else "顯示",
//        modifier = Modifier.clickable {
//            checkpasswordVisible = !checkpasswordVisible
//        }
//    )
//},
//isError = viewModel.checkpasswordError,
//shape = RoundedCornerShape(8.dp),
//visualTransformation = if (checkpasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//singleLine = true,
//colors = TextFieldDefaults.colors(
//focusedIndicatorColor = Color.Blue,
//unfocusedIndicatorColor = Color.Gray,
//),
//modifier = Modifier
//.fillMaxWidth()
//.padding(top = 10.dp)
//)
//if (viewModel.checkpasswordError) {
//    Text(
//        text = "密碼需輸入相同",
//        color = Color.Red,
//        fontSize = 12.sp,
//        modifier = Modifier.padding(start = 16.dp)
//    )
//}
//
//
//
//
////startDestination = Screens.Signup.name