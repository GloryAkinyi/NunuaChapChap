package com.glory.nunuachapchap.ui.theme.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.glory.nunuachapchap.navigation.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Home", fontSize = 20.sp) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { navController.navigate(ROUT_ADD_PRODUCT) }) {
                    Text(text = "Add Product")
                }
            }
        }

        // **Overlay when drawer is open** (Clicking it closes the drawer)
        if (drawerState.isOpen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)) // Semi-transparent overlay
                    .clickable { scope.launch { drawerState.close() } } // Closes on click
            )
        }

        // **Navigation Drawer**
        if (drawerState.isOpen) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(280.dp) // Adjust width for better appearance
                    .background(Color(0xFF615A5D)) // Cute pastel pink background
                    .padding(top = 56.dp) // Start below the top bar
            ) {
                DrawerContent(navController, drawerState, scope)
            }
        }
    }
}

@Composable
fun DrawerContent(navController: NavController, drawerState: DrawerState, scope: CoroutineScope) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // **Drawer Header with Close Button**
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Explore", fontSize = 24.sp, color = Color.White, modifier = Modifier.weight(1f))
            IconButton(onClick = { scope.launch { drawerState.close() } }) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
            }
        }

        Divider(color = Color.White, thickness = 1.dp)

        DrawerItem("Home", Icons.Default.Home) {
            navController.navigate(ROUT_HOME)
            scope.launch { drawerState.close() }
        }

        DrawerItem("Product List", Icons.Default.List) {
            navController.navigate(ROUT_PRODUCT_LIST)
            scope.launch { drawerState.close() }
        }

        DrawerItem("Add Product", Icons.Default.AddCircle) {
            navController.navigate(ROUT_ADD_PRODUCT)
            scope.launch { drawerState.close() }
        }

        DrawerItem("About", Icons.Default.Info) {
            navController.navigate(ROUT_ABOUT)
            scope.launch { drawerState.close() }
        }

        DrawerItem("Login", Icons.Default.Person) {
            navController.navigate(ROUT_LOGIN)
            scope.launch { drawerState.close() }
        }

        DrawerItem("Register", Icons.Default.Person) {
            navController.navigate(ROUT_REGISTER)
            scope.launch { drawerState.close() }
        }
    }
}

@Composable
fun DrawerItem(title: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp)), // Styled item
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, modifier = Modifier.size(24.dp), tint = Color(0xFF6200EA))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = title, fontSize = 18.sp, color = Color(0xFF6200EA)) // Readable text
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
