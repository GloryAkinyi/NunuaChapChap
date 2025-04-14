package com.glory.nunuachapchap.ui.theme.screens.task


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.glory.nunuachapchap.model.Task
import com.glory.nunuachapchap.viewmodel.TaskViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadTaskScreen(
    navController: NavController,
    taskViewModel: TaskViewModel,
    editingTaskId: Int? = null
) {

    //Scaffold

    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        //TopBar
        topBar = {
            TopAppBar(
                title = { Text("Upload Task") },
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

                val context = LocalContext.current


                //Main Contents of the page

                var title by remember { mutableStateOf("") }
                var description by remember { mutableStateOf("") }
                var quantity by remember { mutableStateOf("") }
                var date by remember { mutableStateOf("") }




                LaunchedEffect(editingTaskId) {
                    if (editingTaskId != null) {
                        taskViewModel.loadTaskById(editingTaskId)
                    }
                }

                val editingTask = taskViewModel.selectedTask.collectAsState().value

                LaunchedEffect(editingTask) {
                    editingTask?.let {
                        title = it.title
                        quantity = it.quantity
                        date = it.date
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


                    // Start of Text Field with a dropdown
                    var mExpanded by remember { mutableStateOf(false) }
                    val options = listOf("Cooking", "Washing", "Mopping")
                    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}
                    val icon = if (mExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

                    Column() {
                        OutlinedTextField(
                            value = quantity,
                            onValueChange = { quantity = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    mTextFieldSize = coordinates.size.toSize()
                                },
                            label = {Text("Choose task category")},
                            trailingIcon = {
                                Icon(icon,"contentDescription",
                                    Modifier.clickable { mExpanded = !mExpanded })
                            }
                        )
                        DropdownMenu(
                            expanded = mExpanded,
                            onDismissRequest = { mExpanded = false },
                            modifier = Modifier.width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
                        ) {
                            options.forEach {

                                    label -> DropdownMenuItem(
                                text = { Text(text = label)},
                                onClick = {
                                    quantity = label
                                    mExpanded = false
                                })


                            }
                        }
                    }
                    //End of TextField with dropdown


                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth()
                    )


                    Spacer(modifier = Modifier.height(8.dp))

                    // Date and Time Picker

                    Row(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                        Button(
                            onClick = {
                                val calendar = Calendar.getInstance()
                                val year = calendar.get(Calendar.YEAR)
                                val month = calendar.get(Calendar.MONTH)
                                val day = calendar.get(Calendar.DAY_OF_MONTH)

                                android.app.DatePickerDialog(
                                    context,
                                    { _, selectedYear, selectedMonth, selectedDay ->
                                        val selectedDate = "${selectedDay}/${selectedMonth + 1}/${selectedYear}"

                                        // TimePicker inside DatePicker callback
                                        val hour = calendar.get(Calendar.HOUR_OF_DAY)
                                        val minute = calendar.get(Calendar.MINUTE)

                                        android.app.TimePickerDialog(
                                            context,
                                            { _, selectedHour, selectedMinute ->
                                                val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                                                date = "$selectedDate $selectedTime"
                                            },
                                            hour,
                                            minute,
                                            true
                                        ).show()
                                    },
                                    year,
                                    month,
                                    day
                                ).show()
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(Color.Gray),
                            modifier = Modifier
                                .height(65.dp)
                                .padding(top = 10.dp)
                        ) {
                            Text(text = "Date & Time")
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        OutlinedTextField(
                            value = date,
                            onValueChange = { /* No-op */ },
                            label = { Text("Select") },
                            readOnly = true,
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .width(250.dp),
                            trailingIcon = {
                                Text(text = "📅")
                            },
                            singleLine = true
                        )
                    }


                    //End of a datefield


                    Spacer(Modifier.height(16.dp))




                    Button(onClick = {
                        val task = Task(
                            id = editingTask?.id ?: 0,
                            title = title,
                            quantity = quantity,
                            date = date,
                            description = description
                        )
                        if (editingTask != null) {
                            taskViewModel.update(task)
                        } else {
                            taskViewModel.insert(task)
                        }
                        navController.popBackStack()
                    },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(if (editingTask != null) "Update Task" else "Upload Task")
                    }
                }




            }
        }
    )

    //End of scaffold

}
