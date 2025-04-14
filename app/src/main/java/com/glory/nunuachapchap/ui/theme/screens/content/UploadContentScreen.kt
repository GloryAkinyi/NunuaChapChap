package com.glory.nunuachapchap.ui.theme.screens.content

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.glory.nunuachapchap.model.Content
import com.glory.nunuachapchap.viewmodel.ContentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadContentScreen(
    navController: NavController,
    contentViewModel: ContentViewModel,
    editingContentId: Int? = null
) {

    //Scaffold

    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        //TopBar
        topBar = {
            TopAppBar(
                title = { Text("Upload Content") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back/nav */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.LightGray,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },

        //BottomBar
        bottomBar = {
            NavigationBar(
                containerColor = Color.LightGray
            ){
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedIndex == 0,
                    onClick = { selectedIndex = 0
                        //navController.navigate(ROUT_HOME)
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
                    label = { Text("Favorites") },
                    selected = selectedIndex == 1,
                    onClick = { selectedIndex = 1
                        // navController.navigate(ROUT_HOME)
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = selectedIndex == 2,
                    onClick = { selectedIndex = 2
                        //  navController.navigate(ROUT_HOME)
                    }
                )

            }
        },

        //FloatingActionButton
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Add action */ },
                containerColor = Color.LightGray
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {


                //Main Contents of the page

                var title by remember { mutableStateOf("") }
                var description by remember { mutableStateOf("") }

                LaunchedEffect(editingContentId) {
                    if (editingContentId != null) {
                        contentViewModel.loadContentById(editingContentId)
                    }
                }

                val editingContent = contentViewModel.selectedContent.collectAsState().value

                LaunchedEffect(editingContent) {
                    editingContent?.let {
                        title = it.title
                        description = it.description
                    }
                }

                Column(Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    Button(onClick = {
                        val content = Content(
                            id = editingContent?.id ?: 0,
                            title = title,
                            description = description
                        )
                        if (editingContent != null) {
                            contentViewModel.update(content)
                        } else {
                            contentViewModel.insert(content)
                        }
                        navController.popBackStack()
                    }) {
                        Text(if (editingContent != null) "Update Content" else "Upload Content")
                    }
                }




            }
        }
    )

    //End of scaffold

}


