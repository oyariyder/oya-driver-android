package com.oyariyders.driver.presentation.biodata

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.oyariyders.driver.R
import com.oyariyders.driver.domain.model.Driver
import com.oyariyders.driver.domain.model.UserData
import com.oyariyders.driver.presentation.UiEvent
import com.oyariyders.driver.presentation.loginemail.isValidEmail
import com.oyariyders.driver.ui.theme.PlusJakartaSansFontFamily
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BioData(
    navController: NavController,
    viewModel: BioDataViewModel,
    phoneNumber: String? = null,
    email: String? = null,
    token: String,
){
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val shouldContinue by viewModel.shouldContinue.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phoneNo by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var navigateToOnboard by remember { mutableStateOf(false) }

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
        LaunchedEffect(true) {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is UiEvent.ShowMessage -> {
                        snackbarHostState.showSnackbar(message = event.message)
                    }

                    is UiEvent.Loading -> {
                        viewModel.setLoadingDialogState(event.boolean)
                    }

                    is UiEvent.ShowSuccess -> {
                        navigateToOnboard = true
                    }
                }
            }
        }
        LaunchedEffect(navigateToOnboard) {
            if (navigateToOnboard) {
                // 3. Perform the navigation here.
                // This effect won't be cancelled by the loading state change.
                navController.navigate("Onboarding")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            // You can now use signUpUiState to control dialogs, show errors, etc.
            if (isLoading) {
                Dialog(onDismissRequest = {
                    viewModel.setLoadingDialogState(false)
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
            Spacer(Modifier.height(10.dp))
            Text(
                "Enter your name as it appears on your ID or Passport",
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = PlusJakartaSansFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(18.dp))
            TextField(
                value = firstName,
                isError = firstName.isEmpty(),
                shape = RoundedCornerShape(12.dp),
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
                    errorIndicatorColor = Color.Transparent,

                    // Optional: Keep the container color for better visual separation
                    // If you don't set this, it defaults to the surface color.
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                onValueChange = {
                    firstName = it
                    viewModel.toggleButtonState(firstName.isNotEmpty())
                },
                placeholder = { Text("Your first name") }
            )
            Spacer(Modifier.height(8.dp))
            TextField(
                value = lastName,
                isError = lastName.isEmpty(),
                shape = RoundedCornerShape(12.dp),
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                colors = TextFieldDefaults.colors(
                    // Set all indicator colors to transparent for all states:
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,

                    // Optional: Keep the container color for better visual separation
                    // If you don't set this, it defaults to the surface color.
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                onValueChange = {
                    lastName = it
                    viewModel.toggleButtonState(lastName.isNotEmpty())
                },
                placeholder = { Text("Your last name") }
            )
            if (email.isNullOrEmpty()){
                Spacer(Modifier.height(8.dp))
                TextField(
                    value = emailAddress,
                    isError = emailAddress.isEmpty(),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = TextStyle(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    colors = TextFieldDefaults.colors(
                        // Set all indicator colors to transparent for all states:
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,

                        // Optional: Keep the container color for better visual separation
                        // If you don't set this, it defaults to the surface color.
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    onValueChange = {
                        emailAddress = it
                        viewModel.toggleButtonState(emailAddress.isNotEmpty())
                    },
                    placeholder = { Text("Your Email address") }
                )
                Spacer(Modifier.height(8.dp))
            }
            if (phoneNumber.isNullOrEmpty()){
                Spacer(Modifier.height(8.dp))
                TextField(
                    value = phoneNo,
                    isError = phoneNo.isEmpty(),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = TextStyle(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    colors = TextFieldDefaults.colors(
                        // Set all indicator colors to transparent for all states:
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,

                        // Optional: Keep the container color for better visual separation
                        // If you don't set this, it defaults to the surface color.
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    onValueChange = {
                        phoneNo = it
                        viewModel.toggleButtonState(phoneNo.isNotEmpty())
                    },
                    placeholder = { Text("Your Phone Number") }
                )
                Spacer(Modifier.height(8.dp))
            }
            Spacer(Modifier.height(48.dp))
            Button(
                onClick = {
                    val userData = UserData(firstName, if(phoneNumber.isNullOrEmpty()) phoneNo else phoneNumber)
                    val driver = Driver(data = userData)
                    scope.launch {
                        viewModel.signUp(driver, token)
                    }
                },
                enabled = shouldContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
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