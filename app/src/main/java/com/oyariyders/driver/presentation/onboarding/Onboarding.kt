package com.oyariyders.driver.presentation.onboarding

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.GetCredentialRequest
import androidx.navigation.NavController
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.oyariyders.driver.R
import com.oyariyders.driver.generateSecureRandomNonce
import com.oyariyders.driver.presentation.loginemail.isValidEmail
import com.oyariyders.driver.signIn
import com.oyariyders.driver.ui.theme.mostBlack
import com.oyariyders.driver.ui.theme.DarkGrayTone
import com.oyariyders.driver.ui.theme.GrayDark
import com.oyariyders.driver.ui.theme.PlusJakartaSansFontFamily
import com.oyariyders.driver.utils.AdaptiveBackground
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(navController: NavController){
    val background = adaptiveBackgroundProvider()
    val scope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val image = painterResource(getAdaptiveImage())

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
            )
        },
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            when(background){
                is AdaptiveBackground.ImageResource -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFE3E5F0))
                    ) {
                        Image(
                            painter = painterResource(background.resourceId),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds,
                        )
                        Text(
                            "A few things to set up before you begin",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontFamily = PlusJakartaSansFontFamily,
                            modifier = Modifier.align(Alignment.Center),
                            textAlign = TextAlign.Center,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                is AdaptiveBackground.SolidColor -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(background.color)
                    ) {
                        Text(
                            "A few things to set up before you begin",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontFamily = PlusJakartaSansFontFamily,
                            modifier = Modifier.align(Alignment.Center),
                            textAlign = TextAlign.Center,
                            fontSize = 32.sp,
                            lineHeight = 36.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                is AdaptiveBackground.SolidBrush -> TODO()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                   verticalAlignment = Alignment.CenterVertically

            ) {
                Icon(
                    painter = painterResource(R.drawable.location_add),
                    contentDescription = "Location Add Icon",
                    tint = getAdaptiveTintColor(),
                )
                Spacer(Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                ) {
                    Text(
                        "Select your location",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Choose the city where you’ll be driving and earning.",
                        color = getAdaptiveSubtitleTextColor(),
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Icon(
                    painter = painterResource(R.drawable.profile_tick),
                    contentDescription = "Profile Tick Icon",
                    tint = getAdaptiveTintColor()
                )
                Spacer(Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                ) {
                    Text(
                        "Add a profile picture",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Upload a clear photo for easy recognition and identity verification.",
                        color = getAdaptiveSubtitleTextColor(),
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Icon(
                    painter = painterResource(R.drawable.driverlicense),
                    contentDescription = "Driver License Icon",
                    tint = getAdaptiveTintColor()
                )
                Spacer(Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                ) {
                    Text(
                        "Driver  License",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Add a valid driver’s license for the location you want to drive in.",
                        color = getAdaptiveSubtitleTextColor(),
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Icon(
                    painter = painterResource(R.drawable.car_icon),
                    contentDescription = "Car Icon",
                    tint = getAdaptiveTintColor()
                )
                Spacer(Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                ) {
                    Text(
                        "Select Vehicle type",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Pick the type of vehicle you’ll use for rides",
                        color = getAdaptiveSubtitleTextColor(),
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Icon(
                    painter = painterResource(R.drawable.document_text),
                    contentDescription = "Document Icon",
                    tint = getAdaptiveTintColor()
                )
                Spacer(Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                ) {
                    Text(
                        "Vehicle Document",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Provide the Insurance Policy documents and Vehicle Inspection Report for your vehicle to be approved.",
                        color = getAdaptiveSubtitleTextColor(),
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Spacer(Modifier.height(48.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement =  Arrangement.Center
            ) {
                Button(
                    onClick = {
                        // 1. Launch a coroutine to handle the suspend function
                        scope.launch {
                            navController.navigate("Welcome")
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    shape = RoundedCornerShape(16), // Set the shape to RectangleShape
                    colors = ButtonDefaults.buttonColors(
                        containerColor =  MaterialTheme.colorScheme.inverseSurface,  // Set the button's background color
                        contentColor = MaterialTheme.colorScheme.inverseOnSurface, // Set the color for the content (Icon and Text)
                    ),
                    contentPadding = PaddingValues(all = 12.dp)
                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Continue")
                    }
                }
            }
        }
    }
}

@Composable
fun getAdaptiveSubtitleTextColor(): Color {
    // isSystemInDarkTheme() returns true if the system's dark theme is currently active.
    return if (isSystemInDarkTheme()) {
        GrayDark // Lighter color for better contrast in Dark Mode
    } else {
        Color.Black // Original color for Light Mode
    }
}

@Composable
fun getAdaptiveTintColor(): Color {
    return if (isSystemInDarkTheme()){
        DarkGrayTone
    } else {
        mostBlack
    }
}

@Composable
fun getAdaptiveImage(): Int{
    return if (isSystemInDarkTheme()){
        R.drawable.darkrectangle
    } else {
        R.drawable.rectangle
    }
}

@Composable
fun adaptiveBackgroundProvider(): AdaptiveBackground {
    return if (isSystemInDarkTheme()) {
        // Dark Mode: Return an almost transparent background (fully transparent Black in this case)
        // If you wanted 'almost' transparent, you could use Color(0x22000000) for 13.7% opacity black
        AdaptiveBackground.SolidColor(Color.Transparent)
    } else {
        // Light Mode: Return the image resource ID
        AdaptiveBackground.ImageResource(R.drawable.rectangle)
    }
}
