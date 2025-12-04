package com.oyariyders.driver.presentation.loginemail

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import android.util.Patterns
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.oyariyders.driver.R
import com.oyariyders.driver.generateSecureRandomNonce
import com.oyariyders.driver.presentation.UiEvent
import com.oyariyders.driver.signIn
import com.oyariyders.driver.ui.theme.PlusJakartaSansFontFamily
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginEmailPage(
    navController: NavController,
    loginEmailViewModel: LoginEmailViewModel,
    phoneNo: String,
    token: String,
    ){
    val scope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    val isLoading by loginEmailViewModel.isLoading.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    // Create a local state to trigger navigation
    var navigateToOtpScreen by remember { mutableStateOf(false) }

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
//        LaunchedEffect(true) {
//            loginEmailViewModel.eventFlow.collectLatest { event ->
//                when (event) {
//                    is UiEvent.ShowMessage -> {
//                        snackbarHostState.showSnackbar(message = event.message)
//                    }
//
//                    is UiEvent.Loading -> {
//                        loginEmailViewModel.setLoadingDialogState(event.boolean)
//                    }
//                    is UiEvent.ShowSuccess -> {
//                        navigateToOtpScreen = true
//                    }
//                }
//            }
//        }
//        LaunchedEffect(navigateToOtpScreen) {
//            if (navigateToOtpScreen) {
//                // 3. Perform the navigation here.
//                // This effect won't be cancelled by the loading state change.
//                navController.navigate("EmailOtp/fullPhoneNumber=${phoneNo}&accessToken=${token}")
//            }
//        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
//            if (isLoading) {
//                Dialog(onDismissRequest = {
//                    loginEmailViewModel.setLoadingDialogState(false)
//                })
//                {
//                    Column(
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        CircularProgressIndicator(
//                            modifier = Modifier.width(64.dp),
//                            color = MaterialTheme.colorScheme.secondary,
//                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
//                        )
//                    }
//                }
//            }
            Spacer(Modifier.height(10.dp))
            Text(
                "Enter your email address",
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = PlusJakartaSansFontFamily,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))
            TextField(
                value = email,
                shape = RoundedCornerShape(12.dp),
                isError = !isValidEmail(email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                ),
                colors = TextFieldDefaults.colors(
                    // Set all indicator colors to transparent for all states:
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    // Optional: Keep the container color for better visual separation
                    // If you don't set this, it defaults to the surface color.
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                onValueChange = { email = it },
                placeholder = {
                    Row {
                        Icon(
                            painter = painterResource(R.drawable.email_placeholder),
                            contentDescription = "Email Placeholder Icon"
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Email address")
                    }
                }
            )
            Spacer(Modifier.height(8.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(horizontal = 12.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                ) {

                    Text(
                        text = "Continue with socials",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Spacer(Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable(enabled = true, onClick = {
                                val webClientId = "385918954773-5pr7pj21nh6kp5vvkkcmane6a40b3a16.apps.googleusercontent.com"
                                val signInWithGoogleOption: GetSignInWithGoogleOption = GetSignInWithGoogleOption
                                    .Builder(serverClientId = webClientId)
                                    .setNonce(generateSecureRandomNonce())
                                    .build()

                                val request: GetCredentialRequest = GetCredentialRequest.Builder()
                                    .addCredentialOption(signInWithGoogleOption)
                                    .build()

                                coroutineScope.launch {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                                        signIn(request, context, navController)
                                    }
                                }
                            })
                        ) {
                            Image(
                                painter = painterResource(R.drawable.google),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp) // Adjust size of the image
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Continue with Google",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.arrow_right),
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    contentDescription = "Arrow Forward"
                                )
                            }
                        }
                    }
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
                        //loginEmailViewModel.sendEmailOtp(email)
                        navController.navigate("EmailOtp/fullPhoneNumber=${phoneNo}&accessToken=${token}")
                    },
                    enabled = isValidEmail(email),
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16), // Set the shape to RectangleShape
                    colors = ButtonDefaults.buttonColors(
                        containerColor =  MaterialTheme.colorScheme.inverseSurface, // Set the button's background color
                        contentColor = MaterialTheme.colorScheme.inverseOnSurface, // Set the color for the content (Icon and Text)
                    ),
                    contentPadding = PaddingValues(all = 12.dp)
                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Continue")
                        Spacer(modifier = Modifier.width(8.dp)) // Adds space between icon and text
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Add Icon"
                        )
                    }
                }
            }
        }
    }
}

fun isValidEmail(email: String): Boolean {
    // 1. Check if the string is blank (null, empty, or whitespace)
    if (email.isBlank()) {
        return false
    }
    // 2. Use the built-in Android SDK pattern to match the email
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}