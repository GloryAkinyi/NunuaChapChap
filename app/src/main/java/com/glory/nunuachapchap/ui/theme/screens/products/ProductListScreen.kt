package com.glory.nunuachapchap.ui.theme.screens.products


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.glory.nunuachapchap.R
import com.glory.nunuachapchap.viewmodel.ProductViewModel
import com.glory.nunuachapchap.model.Product
import com.glory.nunuachapchap.navigation.ROUT_ADD_PRODUCT
import com.glory.nunuachapchap.navigation.ROUT_EDIT_PRODUCT
import com.glory.nunuachapchap.navigation.ROUT_PRODUCT_LIST
import com.glory.nunuachapchap.navigation.editProductRoute
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(navController: NavController, viewModel: ProductViewModel) {
    val productList by viewModel.allProducts.observeAsState(emptyList())
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Products", fontSize = 20.sp) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(Color.LightGray),
                actions = {

                    //Menu with navigation
                    IconButton(onClick = { showMenu = true }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Product List") },
                            onClick = {
                                navController.navigate(ROUT_PRODUCT_LIST)
                                showMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Add Product") },
                            onClick = {
                                navController.navigate(ROUT_ADD_PRODUCT)
                                showMenu = false
                            }
                        )
                    }
                    //End of menu with navigation
                }
            )
        },
        bottomBar = { BottomNavigationBar1(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn {
                items(productList.size) { index ->
                    ProductItem(navController, productList[index], viewModel)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


        }
    }
}



@Composable
fun ProductItem(navController: NavController, product: Product, viewModel: ProductViewModel) {
    val painter: Painter = rememberAsyncImagePainter(
        model = product.imagePath?.let { Uri.parse(it) } ?: Uri.EMPTY
    )
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                if (product.id != 0) {
                    navController.navigate(ROUT_EDIT_PRODUCT)
                }
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Product Image
            Image(
                painter = painter,
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .align(Alignment.BottomStart)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                        )
                    )
            )

            // Product Info
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 12.dp, bottom = 60.dp)
            ) {
                Text(
                    text = product.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Price: Ksh${product.price}",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            // Buttons (Message, Edit, Delete, Download PDF)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Message Seller
                    OutlinedButton(
                        onClick = {
                            val smsIntent = Intent(Intent.ACTION_SENDTO)
                            smsIntent.data = "smsto:${product.phone}".toUri()
                            smsIntent.putExtra("sms_body", "Hello Seller,...?")
                            context.startActivity(smsIntent)
                        },
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Row {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Message Seller"
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(text = "Message Seller")
                        }
                    }

                    // Edit Product
                    IconButton(
                        onClick = {
                            navController.navigate(editProductRoute(product.id))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.White
                        )
                    }

                    // Delete Product
                    IconButton(
                        onClick = { viewModel.deleteProduct(product) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.White
                        )
                    }

                    // Download PDF
                    IconButton(
                        onClick = { generateProductPDF(context, product) }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_file_download_24),
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}



fun generateProductPDF(context: Context, product: Product) {
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(300, 500, 1).create()  // Increased height for image
    val page = pdfDocument.startPage(pageInfo)
    val canvas = page.canvas
    val paint = android.graphics.Paint()

    // Load image from URI if available
    val bitmap: Bitmap? = try {
        product.imagePath?.let {
            val uri = Uri.parse(it)
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    // Draw Image if available
    bitmap?.let {
        val scaledBitmap = Bitmap.createScaledBitmap(it, 250, 150, false)
        canvas.drawBitmap(scaledBitmap, 25f, 20f, paint)  // Adjusted position
    }

    // Draw Text
    paint.textSize = 16f
    paint.isFakeBoldText = true
    canvas.drawText("Product Details", 80f, 200f, paint)  // Moved down

    paint.textSize = 12f
    paint.isFakeBoldText = false
    canvas.drawText("Name: ${product.name}", 50f, 230f, paint)
    canvas.drawText("Price: Ksh${product.price}", 50f, 250f, paint)
    canvas.drawText("Seller Phone: ${product.phone}", 50f, 270f, paint)

    pdfDocument.finishPage(page)

    // Save PDF
    val downloadsPath =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(downloadsPath, "${product.name}_Details.pdf")

    try {
        val fos = FileOutputStream(file)
        pdfDocument.writeTo(fos)
        pdfDocument.close()
        fos.close()
        Toast.makeText(context, "PDF saved in Downloads!", Toast.LENGTH_LONG).show()
    } catch (e: IOException) {
        e.printStackTrace()
        Toast.makeText(context, "Failed to save PDF!", Toast.LENGTH_LONG).show()
    }
}

// Bottom Navigation Bar Component
@Composable
fun BottomNavigationBar1(navController: NavController) {
    NavigationBar(
        containerColor = Color(0xFFA2B9A2),
        contentColor = Color.White
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(ROUT_PRODUCT_LIST) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Product List") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(ROUT_ADD_PRODUCT) },
            icon = { Icon(Icons.Default.AddCircle, contentDescription = "Add Product") },
            label = { Text("Add") }
        )
    }
}
