package com.oyariyders.driver.presentation.otp

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.oyariyders.driver.SignUpViewModel
import com.oyariyders.driver.R
import com.oyariyders.driver.presentation.UiEvent
import com.oyariyders.driver.presentation.phonenumberoptions.PhoneNumberOptionsEvent
import com.oyariyders.driver.ui.theme.PlusJakartaSansFontFamily
import kotlinx.coroutines.flow.collectLatest
import java.net.URLDecoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(
    navController: NavController,
    screenModel: SignUpViewModel,
    viewModel: OTPScreenViewModel,
    phoneNumber: String? = null
) {
    val snackbarHostState = remember { SnackbarHostState() }
    // 2. Collect the UI state. Your UI will automatically recompose when this state changes.
    val signUpUiState by screenModel.signUpUiState.collectAsState()
    // Observe the states from the ViewModel
    val secondsRemaining by viewModel.secondsRemaining.collectAsState()
    val smsSecondsRemaining by viewModel.smsSecondsRemaining.collectAsState()
    val isTimerRunning by viewModel.isTimerRunning.collectAsState()
    val isSmsTimerRunning by viewModel.isSmsTimerRunning.collectAsState()
    // Create a local state to trigger navigation
    var navigateToEmail by remember { mutableStateOf(false) }

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
        LaunchedEffect(true) {
            screenModel.eventFlow.collectLatest { event ->
                when (event) {
                    is UiEvent.ShowMessage -> {
                        snackbarHostState.showSnackbar(message = event.message)
                    }

                    is UiEvent.Loading -> {
                        screenModel.setLoadingDialogState(event.boolean)
                    }

                    is UiEvent.ShowSuccess -> {
                        screenModel.saveAccessToken(event.message)
                        navigateToEmail = true
                    }
                }
            }
        }

        LaunchedEffect(navigateToEmail) {
            if (navigateToEmail) {
                // 3. Perform the navigation here.
                // This effect won't be cancelled by the loading state change.
                navController.navigate("LoginEmailPage?fullPhoneNumber=${phoneNumber}&accessToken=${screenModel.accessToken.value}")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
        ){
            // You can now use signUpUiState to control dialogs, show errors, etc.
            if (signUpUiState.showLoadingDialog) {
                Dialog(onDismissRequest = {
                    screenModel.setLoadingDialogState(false)
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
            OtpArea(
                navController = navController,
                phoneNumber = phoneNumber,
                otpLength = 6,
                otpValues = signUpUiState.otpValues,
                isError = signUpUiState.isOtpError,
                viewModel = viewModel,
                secondsRemaining = secondsRemaining,
                isTimerRunning = isTimerRunning,
                smsSecondsRemaining = smsSecondsRemaining,
                isSmsTimerRunning = isSmsTimerRunning,
                onUpdateOtpValue = { index, value ->
                    screenModel.updateOtpValue(index, value)
                },
                onVerifyOtp = {
                    // 1. Call the ViewModel to handle the verification logic
                    screenModel.verifyOtp(phoneNumber ?: "")
                },
                screenModel = screenModel
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    screenModel.verifyOtp(phoneNumber ?: "")
                },
                //enabled = shouldContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                shape = RoundedCornerShape(16), // Set the shape to RectangleShape
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.inverseSurface, // Set the button's background color
                    contentColor =  MaterialTheme.colorScheme.inverseOnSurface,// Set the color for the content (Icon and Text)
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
    viewModel: OTPScreenViewModel,
    screenModel: SignUpViewModel,
    isTimerRunning: Boolean,
    secondsRemaining: Long,
    isSmsTimerRunning: Boolean,
    smsSecondsRemaining: Long,
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
            color = MaterialTheme.colorScheme.onSurface,
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
                    screenModel.onEvent(PhoneNumberOptionsEvent.EnteredPhoneNum(phoneNumber ?: ""))
                    //Resend Whatsapp OTP
                    viewModel.startTimer()
                },
                enabled = !isTimerRunning,
                label = {
                    Row {
                        Text(
                            "Get code on WhatsApp",
                        )
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
                        painter = painterResource(R.drawable.whatsapp),
                        contentDescription = "Whatsapp Selection",
                        modifier = Modifier.size(AssistChipDefaults.IconSize),
                        tint = if (isTimerRunning) Color.LightGray else MaterialTheme.colorScheme.onSurface,
                    )
                }
            )
            AssistChip(
                onClick = {
                    screenModel.onEvent(PhoneNumberOptionsEvent.EnteredPhoneNum(phoneNumber ?: ""))
                    //Resend SMS OTP
                    viewModel.startSmsTimer()
                },
                enabled = !isSmsTimerRunning,
                label = {
                    Row {
                        Text(
                            "Get SMS  code",
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isSmsTimerRunning) {
                                "($smsSecondsRemaining)"
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
                        painter = painterResource(R.drawable.message_text),
                        contentDescription = "SMS Selection",
                        modifier = Modifier.size(AssistChipDefaults.IconSize),
                        tint = if (isTimerRunning) Color.LightGray else MaterialTheme.colorScheme.onSurface,
                    )
                }
            )
        }
    }
}

@Composable
fun OTPInputTextFields(
    otpLength: Int,
    onUpdateOtpValuesByIndex: (Int, String) -> Unit,
    onOtpInputComplete: () -> Unit,
    modifier: Modifier = Modifier,
    otpValues: List<String> = List(otpLength) { "" }, // Pass this as default for future reference
    isError: Boolean = false,
) {
    val focusRequesters = List(otpLength) { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
//        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally)
    ) {
        otpValues.forEachIndexed { index, value ->
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .focusRequester(focusRequesters[index])
                    .onKeyEvent { keyEvent ->
                        if (keyEvent.key == Key.Backspace) {
                            if (otpValues[index].isEmpty() && index > 0) {
                                onUpdateOtpValuesByIndex(index, "")
                                focusRequesters[index - 1].requestFocus()
                            } else {
                                onUpdateOtpValuesByIndex(index, "")
                            }
                            true
                        } else {
                            false
                        }
                    },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    // Ensure the background is solid white for the shape to be visible
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                value = value,
                onValueChange = { newValue ->
                    // To use OTP code copied from keyboard
                    if (newValue.length == otpLength) {
                        for (i in otpValues.indices) {
                            onUpdateOtpValuesByIndex(
                                i,
                                if (i < newValue.length && newValue[i].isDigit()) newValue[i].toString() else ""
                            )
                        }

                        keyboardController?.hide()
                        onOtpInputComplete() // you should validate the otp values first for, if it is only digits or isNotEmpty
                    } else if (newValue.length <= 1) {
                        onUpdateOtpValuesByIndex(index, newValue)
                        if (newValue.isNotEmpty()) {
                            if (index < otpLength - 1) {
                                focusRequesters[index + 1].requestFocus()
                            } else {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                                onOtpInputComplete()
                            }
                        }
                    } else {
                        if (index < otpLength - 1) focusRequesters[index + 1].requestFocus()
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = if (index == otpLength - 1) ImeAction.Done else ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        if (index < otpLength - 1) {
                            focusRequesters[index + 1].requestFocus()
                        }
                    },
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        onOtpInputComplete()
                    }
                ),
                shape = RoundedCornerShape(12.dp),
                isError = isError,
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
            )

            LaunchedEffect(value) {
                if (otpValues.all { it.isNotEmpty() }) {
                    focusManager.clearFocus()
                    onOtpInputComplete()
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        focusRequesters.first().requestFocus()
    }
}

/**
 * Masks all but the last four digits of a phone number with asterisks.
 */
 fun maskDecodedNumber(decodedNumber: String): String {
    val visibleCount = 4
    // If the number is short, return it as-is
    if (decodedNumber.length <= visibleCount) return decodedNumber

    val visibleDigits = decodedNumber.takeLast(visibleCount)
    val maskedPrefixLength = decodedNumber.length - visibleCount
    val maskedPrefix = "*".repeat(maskedPrefixLength)

    return "$maskedPrefix$visibleDigits"
}