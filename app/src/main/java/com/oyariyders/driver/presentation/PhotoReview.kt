package com.oyariyders.driver.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.oyariyders.driver.R
import com.oyariyders.driver.ui.theme.DeepOrange
import com.oyariyders.driver.ui.theme.OffWhiteBackground
import com.oyariyders.driver.ui.theme.PlusJakartaSansFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoReview(navController: NavController){
    val scope = rememberCoroutineScope()
    var selected by remember { mutableStateOf(true) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.Transparent,
                ),
                title = {
                    Text("")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        colors =  IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Navigate Back"
                        )
                    }
                }
            )
        },
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Spacer(Modifier.height(8.dp))
            Text(
                "Upload a clear photo for easy recognition and identity verification.",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
                fontFamily = PlusJakartaSansFontFamily,
                fontSize = 24.sp,
                lineHeight = 32.sp,
                modifier = Modifier.padding(16.dp)
            )
            Spacer(Modifier.height(12.dp))
            //DropdownMenuWithDetails()
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(550.dp)
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Your upload",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = PlusJakartaSansFontFamily,
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable._296),
                            contentDescription = null,
                            modifier = Modifier
                                .size(200.dp) // Adjust size of the image
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }
                    Spacer(Modifier.height(24.dp))
                    Text(
                        "We are currently reviewing your image",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = PlusJakartaSansFontFamily,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .background(OffWhiteBackground)
                            .padding(8.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .background(OffWhiteBackground)
                            .padding(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.info_circle),
                            tint = DeepOrange,
                            contentDescription = "Information Icon"
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "The document does not clearly show your face, please upload an image that shows your face",
                            color = MaterialTheme.colorScheme.error,
                            fontFamily = PlusJakartaSansFontFamily,
                            fontStyle = FontStyle.Italic,
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement =  Arrangement.Bottom
        ) {
            Button(
                onClick = {
                    navController.navigate("PhotoReview")
                },
                modifier = Modifier
                    .fillMaxWidth()
                ,
                shape = RoundedCornerShape(16), // Set the shape to RectangleShape
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.inverseSurface, // Set the button's background color
                    contentColor = MaterialTheme.colorScheme.inverseOnSurface  // Set the color for the content (Icon and Text)
                ),
                contentPadding = PaddingValues(all = 12.dp)
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Upload image",
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Adds space between icon and text
                    Icon(
                        painter = painterResource(R.drawable.document_upload),
                        contentDescription = "Upload Icon"
                    )
                }
            }
        }
    }
}