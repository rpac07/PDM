package pdm.app.listacompras.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pdm.app.listacompras.models.ListItem
import pdm.app.listacompras.ui.theme.appFontBold16

@Composable
fun ListTypeRowView(modifier: Modifier = Modifier, listItem: ListItem, onEdit: (ListItem) -> Unit, onDelete: (ListItem) -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth() // Ocupa a largura total disponível
            .padding(8.dp) // Espaçamento para as bordas
            .border(1.dp, color = MaterialTheme.colorScheme.primary) // Bordas da "tabela"
            .padding(16.dp) // Padding interno para separar o conteúdo das bordas
    ) {
        Column(
            modifier = Modifier.fillMaxSize(), // Deixe a coluna crescer conforme necessário
            verticalArrangement = Arrangement.spacedBy(8.dp) // Espaçamento entre os elementos
        ) {
            // Linha contendo o título e os ícones
            Row(
                modifier = Modifier
                    .fillMaxWidth(), // Ocupa toda a largura
                verticalAlignment = Alignment.CenterVertically, // Alinha o título e os ícones verticalmente no centro
                horizontalArrangement = Arrangement.SpaceBetween // Coloca o título à esquerda e os ícones à direita
            ) {
                // Título não editável
                Text(
                    text = listItem.name ?: "",
                    style = appFontBold16,
                    modifier = Modifier
                        .padding(4.dp) // Padding ao redor do título
                )

                // Ícones de edição e exclusão alinhados à direita
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Espaço entre os ícones
                ) {
                    // Ícone de edição
                    IconButton(onClick = { onEdit(listItem) }) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit")
                    }

                    // Ícone de exclusão
                    IconButton(onClick = { onDelete(listItem) }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }
            }

            // Linha divisória
            HorizontalDivider(
                thickness = 1.dp, // Espessura da linha
                modifier = Modifier.padding(vertical = 4.dp) // Espaçamento acima e abaixo da linha
            )

            // Descrição com alinhamento e estilo diferenciado
            Text(
                text = listItem.description ?: "",
                modifier = Modifier.padding(start = 8.dp) // Alinha a descrição com um pequeno deslocamento
            )
        }
    }
}
