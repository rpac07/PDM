package pdm.app.listacompras.pages.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.ui.Alignment

import pdm.app.listacompras.repository.ListItemRepository

@Composable
fun EditListPage(
    navController: NavController,
    listItemName: String // Passamos o nome do item aqui
) {
    var itemName = remember { mutableStateOf(listItemName) }
    var itemDescription = remember { mutableStateOf("") }

    // Carregar o item ao entrar na página
    LaunchedEffect(itemName.value) {
        // Carregar o item a partir do nome
        ListItemRepository.get(itemName.value) { item ->
            itemDescription.value = item?.description ?: ""
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "A editar: ${itemName.value}")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = itemName.value,
            onValueChange = { itemName.value = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = itemDescription.value,
            onValueChange = { itemDescription.value = it },
            label = { Text("Descrição") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Chamada para salvar as alterações
            ListItemRepository.update(itemName.value, itemDescription.value) {
                navController.popBackStack() // Volta à página anterior
            }
        }) {
            Text(text = "Guardar")
        }
    }
}
