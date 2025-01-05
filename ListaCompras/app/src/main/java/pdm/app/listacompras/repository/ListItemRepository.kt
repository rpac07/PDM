package pdm.app.listacompras.repository

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pdm.app.listacompras.TAG
import pdm.app.listacompras.models.ListItem

object ListItemRepository {

    val db = Firebase.firestore

    // Função para adicionar um ListItem
    fun add(listItem: ListItem, onAddListSuccess: () -> Unit) {
        val currentUser = Firebase.auth.currentUser

        currentUser?.uid?.let {
            listItem.owners = arrayListOf(it)
        }

        db.collection("listTypes")
            .add(listItem)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                onAddListSuccess()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    // Função para obter todos os ListItems
    fun getAll(onSuccess: (List<ListItem>) -> Unit) {
        db.collection("listTypes")
            .whereArrayContains("owners", Firebase.auth.currentUser?.uid!!)
            .addSnapshotListener { value, error ->
                val listItems = value?.documents?.mapNotNull {
                    val itemList = it.toObject(ListItem::class.java)
                    itemList?.docId = it.id
                    itemList
                }
                listItems?.let { onSuccess(it) }
            }
    }

    // Função para buscar um ListItem pelo nome
    fun get(itemName: String, onResult: (ListItem?) -> Unit) {
        db.collection("listTypes")
            .whereEqualTo("name", itemName) // Filtra pela propriedade "name"
            .limit(1) // Garante que pegamos apenas um item
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    onResult(null) // Se não encontrar nada, retorna null
                } else {
                    val item = documents.first().toObject(ListItem::class.java)
                    item?.docId = documents.first().id
                    onResult(item) // Retorna o item encontrado
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting document: ", exception)
                onResult(null)
            }
    }

    // Função para atualizar um ListItem
    fun update(itemName: String, newDescription: String, onUpdateSuccess: () -> Unit) {
        db.collection("listTypes")
            .whereEqualTo("name", itemName)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Log.w(TAG, "Item not found for update")
                } else {
                    val item = documents.first()
                    val itemRef = item.reference

                    // Atualiza a descrição do item
                    itemRef.update("description", newDescription)
                        .addOnSuccessListener {
                            Log.d(TAG, "Item updated successfully")
                            onUpdateSuccess() // Chama o callback de sucesso após a atualização
                        }
                        .addOnFailureListener { exception ->
                            Log.w(TAG, "Error updating document: ", exception)
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error finding document: ", exception)
            }
    }

    // Função para excluir um ListItem
    fun delete(itemName: String, onDeleteSuccess: () -> Unit) {
        db.collection("listTypes")
            .whereEqualTo("name", itemName)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Log.w(TAG, "Item not found for delete")
                } else {
                    val item = documents.first()
                    val itemRef = item.reference

                    // Exclui o item
                    itemRef.delete()
                        .addOnSuccessListener {
                            Log.d(TAG, "Item deleted successfully")
                            onDeleteSuccess() // Chama o callback de sucesso após a exclusão
                        }
                        .addOnFailureListener { exception ->
                            Log.w(TAG, "Error deleting document: ", exception)
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error finding document for deletion: ", exception)
            }
    }
}
