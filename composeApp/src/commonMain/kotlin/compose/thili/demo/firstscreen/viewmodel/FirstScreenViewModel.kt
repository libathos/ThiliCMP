package compose.thili.demo.firstscreen.viewmodel

import androidx.lifecycle.ViewModel
import compose.thili.demo.firstscreen.data.FirstScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class FirstScreenViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(FirstScreenState())
    val uiState: StateFlow<FirstScreenState> = _uiState.asStateFlow()


    fun onButtonPushed() {
        _uiState.update {
            it.copy(isButtonPushed = it.isButtonPushed.not())
        }
    }


}