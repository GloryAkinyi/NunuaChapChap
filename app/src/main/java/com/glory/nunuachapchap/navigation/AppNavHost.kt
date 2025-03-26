package com.glory.nunuachapchap.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.glory.nunuachapchap.data.UserDatabase
import com.glory.nunuachapchap.repository.UserRepository
import com.glory.nunuachapchap.ui.theme.screens.about.AboutScreen
import com.glory.nunuachapchap.ui.theme.screens.auth.LoginScreen
import com.glory.nunuachapchap.ui.theme.screens.auth.RegisterScreen
import com.glory.nunuachapchap.ui.theme.screens.home.HomeScreen
import com.glory.nunuachapchap.viewmodel.AuthViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUT_LOGIN
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






    }
}