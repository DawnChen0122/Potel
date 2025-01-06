//import androidx.lifecycle.ViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//
//class AccountViewModel : ViewModel() {
//
//
//    val yearRange = (1924..2025).map { it.toString() }
//    private val _inputYear = MutableStateFlow("")
//    val inputYear = _inputYear.asStateFlow()
//    private val _expandedYear = MutableStateFlow(false)
//    val expandedYear = _expandedYear.asStateFlow()
//    fun onYearChanged(newYear: String) {
//        _inputYear.value = newYear
//        _expandedYear.value = false
//    }
//    fun toggleYearDropdown() {
//        _expandedYear.value = !_expandedYear.value
//    }
//}