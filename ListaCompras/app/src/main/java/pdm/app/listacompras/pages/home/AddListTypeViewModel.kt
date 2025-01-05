package pdm.app.listacompras.pages.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import pdm.app.listacompras.models.ListItem
import pdm.app.listacompras.repository.ListItemRepository

data class AddListTypeState(
    var name: String = "",
    var description: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class AddListTypeViewModel : ViewModel() {

    var state = mutableStateOf(AddListTypeState())
        private set

    private val name
        get() = state.value.name
    private val description
        get() = state.value.description

    fun onNameChange(newValue: String) {
        state.value = state.value.copy(name = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        state.value = state.value.copy(description = newValue)
    }

    fun addList() {
        ListItemRepository.add(listItem = ListItem("", name, description, null)) {

        }
    }
}
