package pdm.app.listacompras.pages.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import pdm.app.listacompras.models.ListItem
import pdm.app.listacompras.repository.ListItemRepository


data class ListState(
    val listItems : List<ListItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ListTypesViewModel : ViewModel() {

    var state = mutableStateOf(ListState())
        private set

    fun addList() {
        ListItemRepository.add(
            ListItem("","title", "description", null)
        ){

        }
    }

    fun loadListTypes(){
        ListItemRepository.getAll { listItems ->
            state.value = state.value.copy(
                listItems = listItems
            )

            for (item in listItems){
                Log.d("TAG", item.name?:"no name")
            }
        }
    }

}