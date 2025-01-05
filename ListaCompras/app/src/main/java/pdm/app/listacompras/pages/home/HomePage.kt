// No arquivo HomePage.kt
package pdm.app.listacompras.pages.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pdm.app.listacompras.AuthViewModel
import pdm.app.listacompras.pages.home.ListTypesViewModel
import pdm.app.listacompras.repository.ListItemRepository


@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel,
    listTypesViewModel: ListTypesViewModel // Passar o ViewModel corretamente
) {
    val authState = authViewModel.authState.observeAsState()
    val listState = listTypesViewModel.state.value // Acessar o estado das listas corretamente

    // Quando o estado de autenticação mudar, navega para a página de login ou home
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthViewModel.AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }

    // Carregar as listas quando a HomePage for exibida
    LaunchedEffect(Unit) {
        listTypesViewModel.loadListTypes()
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start // Alinha o conteúdo à esquerda
        ) {
            Text(text = "   Lista de Compras", modifier = Modifier.align(Alignment.CenterVertically))

            Spacer(modifier = Modifier.weight(1f)) // Preenche o espaço restante

            // Ícone de exclusão
            IconButton(onClick = { authViewModel.logout() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout", Modifier.size(32.dp))
            }
        }
        // Botão para navegar até a página de adicionar lista
        Button(onClick = { navController.navigate("addList") }) {
            Text(text = "+ Add New List")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Exibir os itens de lista com LazyColumn
        if (listState.isLoading) {
            Text(text = "Loading...")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp) // Adiciona um espaçamento entre os itens
            ) {
                items(listState.listItems) { listItem ->
                    ListTypeRowView(
                        listItem = listItem,
                        onEdit = { item ->
                            // Ação de edição: Navegar para EditListPage
                            Log.d("Edit", "Editando: ${item.name}")
                            navController.navigate("editList/${item.name}") // Navega para EditListPage passando o nome
                        },
                        onDelete = { item ->
                            // Exclusão diretamente do nome do item
                            Log.d("Delete", "Excluindo: ${item.name}")
                            ListItemRepository.delete(item.name ?: "") {
                                // Atualiza a lista após a exclusão
                                listTypesViewModel.loadListTypes()
                            }
                          }

                    )
                }
            }

        }
        }

        // Exibir erros, caso existam
        listState.error?.let {
            Text(text = it)
        }
    }
