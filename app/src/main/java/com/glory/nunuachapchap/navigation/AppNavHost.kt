package com.glory.nunuachapchap.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.glory.nunuachapchap.data.UserDatabase
import com.glory.nunuachapchap.repository.UserRepository
import com.glory.nunuachapchap.ui.theme.screens.about.AboutScreen
import com.glory.nunuachapchap.ui.theme.screens.auth.LoginScreen
import com.glory.nunuachapchap.ui.theme.screens.auth.RegisterScreen
import com.glory.nunuachapchap.ui.theme.screens.home.HomeScreen
import com.glory.nunuachapchap.ui.theme.screens.products.AddProductScreen
import com.glory.nunuachapchap.ui.theme.screens.products.EditProductScreen
import com.glory.nunuachapchap.ui.theme.screens.products.ProductListScreen
import com.glory.nunuachapchap.viewmodel.AuthViewModel
import com.glory.nunuachapchap.viewmodel.ProductViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUT_PRODUCT_LIST,
    productViewModel: ProductViewModel = viewModel(),

    ) {

    val context = LocalContext.current


    // Initialize Room Database and Repository for Authentication
    val appDatabase = UserDatabase.getDatabase(context)
    val authRepository = UserRepository(appDatabase.userDao())
    val authViewModel: AuthViewModel = AuthViewModel(authRepository) // Direct instantiation



    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(ROUT_HOME) {
            HomeScreen(navController)
        }
        composable(ROUT_ABOUT) {
            AboutScreen(navController)
        }


        composable(ROUT_REGISTER) {
            RegisterScreen(authViewModel, navController) {  // ✅ Fixed parameter order
                navController.navigate(ROUT_LOGIN) {
                    popUpTo(ROUT_REGISTER) { inclusive = true } // ✅ Prevent back navigation to Register
                }
            }
        }

        composable(ROUT_LOGIN) {
            LoginScreen(authViewModel,navController) {
                navController.navigate(ROUT_HOME) {
                    popUpTo(ROUT_LOGIN) { inclusive = true } // ✅ Prevent going back to login
                }
            }
        }

        //Products

        composable(ROUT_ADD_PRODUCT) {
            AddProductScreen(navController, productViewModel)
        }

        composable(ROUT_PRODUCT_LIST) {
            ProductListScreen(navController, productViewModel)
        }

        composable(
            route = ROUT_EDIT_PRODUCT,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId")
            if (productId != null) {
                EditProductScreen(productId, navController, productViewModel)
            }
        }







    }
}