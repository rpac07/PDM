package pdm.app.listacompras

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pdm.app.listacompras.pages.home.HomePage
import pdm.app.listacompras.pages.LoginPage
import pdm.app.listacompras.pages.SignupPage
import pdm.app.listacompras.pages.home.AddListTypeView
import pdm.app.listacompras.pages.home.EditListPage
import pdm.app.listacompras.pages.home.ListTypesViewModel

@Composable
fun AppNav(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login", builder = {
        composable("login") {
            LoginPage(modifier, navController, authViewModel)
        }
        composable("signup") {
            SignupPage(modifier, navController, authViewModel)
        }
        composable("home") {
            val listTypesViewModel = viewModel<ListTypesViewModel>() // Certifique-se de importar a versão correta do ViewModel
            HomePage(modifier, navController, authViewModel, listTypesViewModel)
        }
        composable("addList") {
            AddListTypeView(navController = navController)
        }
        composable("editList/{itemName}") { backStackEntry ->
            val itemName = backStackEntry.arguments?.getString("itemName")
            if (itemName != null) {
                EditListPage(navController = navController, listItemName = itemName)
            }
        }
    })
}
