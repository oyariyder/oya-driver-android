package com.oyariyders.driver.presentation.phonenumberoptions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.oyariyders.driver.R
import com.oyariyders.driver.presentation.UiEvent
import com.oyariyders.driver.presentation.loginphone.LoginPhoneViewModel
import com.oyariyders.driver.presentation.loginphone.SelectCountryWithCountryCode
import com.oyariyders.driver.ui.theme.PlusJakartaSansFontFamily
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberOptions(
    navController: NavController,
    phoneNumberOptionsViewModel: PhoneNumberOptionsViewModel,
    viewModel: LoginPhoneViewModel,
    phoneNumber: String? = null,
){
    val scope = rememberCoroutineScope()
    val shouldContinue by viewModel.shouldContinue.collectAsState()
    val isLoading by phoneNumberOptionsViewModel.isLoading.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    // Create a local state to trigger navigation
    var navigateToOtpScreen by remember { mutableStateOf(false) }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEECE9))
            .padding(8.dp),
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
                            containerColor = Color.White
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

        LaunchedEffect(true) {
            phoneNumberOptionsViewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is UiEvent.ShowMessage -> {
                        snackbarHostState.showSnackbar(message = event.message)
                    }

                    is UiEvent.Loading -> {
                        phoneNumberOptionsViewModel.setLoadingDialogState(event.boolean)
                    }
                    is UiEvent.ShowSuccess -> {
                        navigateToOtpScreen = true
                    }
                }
            }
        }

        LaunchedEffect(navigateToOtpScreen) {
            if (navigateToOtpScreen) {
                // 3. Perform the navigation here.
                // This effect won't be cancelled by the loading state change.
                navController.navigate("OtpScreen/fullPhoneNumber=${viewModel.phoneNumber.value.toString()}")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEEECE9))
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            // You can now use signUpUiState to control dialogs, show errors, etc.
            if (isLoading) {
                Dialog(onDismissRequest = {
                    phoneNumberOptionsViewModel.setLoadingDialogState(false)
                })
                {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.width(64.dp),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    }
                }
            }
            Text(
                "Enter your phone number",
                color = Color.Black,
                fontFamily = PlusJakartaSansFontFamily,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(12.dp))
            SelectCountryWithCountryCode(
                phoneNumber,
                viewModel = viewModel
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement =  Arrangement.Center
            ) {
                Button(
                    onClick = {
                        // 1. Launch a coroutine to handle the suspend function
                        scope.launch {
                            //Verify that phonenumber isn't empty or invalid
                            phoneNumberOptionsViewModel.onEvent(PhoneNumberOptionsEvent.EnteredPhoneNum(viewModel.phoneNumber.value.toString()))
                            //navController.navigate("OtpScreen/fullPhoneNumber=${viewModel.phoneNumber.value.toString()}")
                        }

                    },
                    enabled = shouldContinue,
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    shape = RoundedCornerShape(16), // Set the shape to RectangleShape
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black, // Set the button's background color
                        contentColor = Color.White // Set the color for the content (Icon and Text)
                    ),
                    contentPadding = PaddingValues(all = 12.dp)
                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Get started",
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        )
                        Spacer(modifier = Modifier.width(8.dp)) // Adds space between icon and text
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            tint = colorResource(R.color.white),
                            contentDescription = "Add Icon"
                        )
                    }
                }
            }
        }
    }
}