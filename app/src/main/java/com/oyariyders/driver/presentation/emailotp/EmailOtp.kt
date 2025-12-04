package com.oyariyders.driver.presentation.emailotp

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.oyariyders.driver.R
import com.oyariyders.driver.SignUpViewModel
import com.oyariyders.driver.presentation.UiEvent
import com.oyariyders.driver.presentation.otp.OTPInputTextFields
import com.oyariyders.driver.presentation.otp.OTPScreenViewModel
import com.oyariyders.driver.presentation.otp.OtpArea
import com.oyariyders.driver.presentation.otp.maskDecodedNumber
import com.oyariyders.driver.presentation.phonenumberoptions.PhoneNumberOptionsEvent
import com.oyariyders.driver.ui.theme.PlusJakartaSansFontFamily
import kotlinx.coroutines.flow.collectLatest
import java.net.URLDecoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailOtp(
    navController: NavController,
    screenModel: SignUpViewModel,
    viewModel: EmailOtpViewModel,
    email: String? = "johnasalu13@gmail.com",
    token: String? = ""
){
    val snackbarHostState = remember { SnackbarHostState() }
    // 2. Collect the UI state. Your UI will automatically recompose when this state changes.
    val signUpUiState by screenModel.signUpUiState.collectAsState()
    // Observe the states from the ViewModel
    val secondsRemaining by viewModel.secondsRemaining.collectAsState()
    val isTimerRunning by viewModel.isTimerRunning.collectAsState()
    // Create a local state to trigger navigation
    var navigateToBioData by remember { mutableStateOf(false) }

    Scaffold(
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
//            screenModel.eventFlow.collectLatest { event ->
//                when (event) {
//                    is UiEvent.ShowMessage -> {
//                        snackbarHostState.showSnackbar(message = event.message)
//                    }
//
//                    is UiEvent.Loading -> {
//                        screenModel.setLoadingDialogState(event.boolean)
//                    }
//
//                    is UiEvent.ShowSuccess -> {
//                        //snackbarHostState.showSnackbar(message = event.message)
//                        navigateToBioData = true
//                    }
//                }
//            }
//        }

//        LaunchedEffect(navigateToBioData) {
//            if (navigateToBioData) {
//                // 3. Perform the navigation here.
//                // This effect won't be cancelled by the loading state change.
//                navController.navigate("BioData")
//            }
//        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
        ){
            // You can now use signUpUiState to control dialogs, show errors, etc.
//            if (signUpUiState.showLoadingDialog) {
//                Dialog(onDismissRequest = {
//                    screenModel.setLoadingDialogState(false)
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
            OtpArea(
                navController = navController,
                phoneNumber = email,
                otpLength = 6,
                otpValues = signUpUiState.otpValues,
                isError = signUpUiState.isOtpError,
                viewModel = viewModel,
                secondsRemaining = secondsRemaining,
                isTimerRunning = isTimerRunning,
                onUpdateOtpValue = { index, value ->
                    screenModel.updateOtpValue(index, value)
                },
                onVerifyOtp = {
                    // 1. Call the ViewModel to handle the verification logic
                    //screenModel.verifyOtp(email ?: "")
                    navController.navigate("BioData?fullPhoneNumber=${email}&accessToken=${token}")
                },
                screenModel = screenModel
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    //screenModel.verifyOtp(email ?: "")
                    navController.navigate("BioData?fullPhoneNumber=${email}&accessToken=${token}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
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
                    Text(
                        "Continue",
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Adds space between icon and text
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Arrow Forward Icon"
                    )
                }
            }
        }
    }
}

@Composable
fun OtpArea(
    modifier: Modifier = Modifier,
    viewModel: EmailOtpViewModel,
    screenModel: SignUpViewModel,
    isTimerRunning: Boolean,
    secondsRemaining: Long,
    navController: NavController,
    btnText: String = "Continue",
    // --- 1. RECEIVE STATE AS PARAMETERS ---
    otpLength: Int,
    otpValues: List<String>,
    isError: Boolean,
    // --- 2. SEND EVENTS AS LAMBDAS ---
    onUpdateOtpValue: (index: Int, value: String) -> Unit,
    onVerifyOtp: () -> Unit,
    phoneNumber: String?
) {
    val decodedNumber = URLDecoder.decode(phoneNumber, "UTF-8")
    // --- New: Create the masked number for display ---
    val maskedNumber = maskDecodedNumber(decodedNumber)

    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(Modifier.height(10.dp))
        Text(
            "We have sent a 6 digit code to $maskedNumber .",
            color = Color.Black,
            fontFamily = PlusJakartaSansFontFamily,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OTPInputTextFields(
                otpLength = otpLength,
                otpValues = otpValues,
                isError = isError,
                onUpdateOtpValuesByIndex = onUpdateOtpValue, // Pass the event up
                onOtpInputComplete = onVerifyOtp // Pass the event up
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(horizontal = 12.dp)
        ){
            AssistChip(
                onClick = {
                    screenModel.resendEmailOtp(phoneNumber ?: "")
                    //Resend Whatsapp OTP
                    viewModel.startTimer()
                },
                enabled = !isTimerRunning,
                label = {
                    Row {
                        Text("Resend Code")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isTimerRunning) {
                                "($secondsRemaining)"
                            } else {
                                ""
                            },
                        )
                    }
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        tint =  if (isTimerRunning) Color.LightGray else MaterialTheme.colorScheme.onSurface,
                        contentDescription = "Resend",
                        modifier = Modifier.size(AssistChipDefaults.IconSize),
                    )
                }
            )
        }
    }
}