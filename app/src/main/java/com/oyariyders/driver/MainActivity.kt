package com.oyariyders.driver

import android.content.ContentValues.TAG
import android.content.Context
import android.credentials.GetCredentialException
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialCustomException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.oyariyders.driver.presentation.biodata.BioData
import com.oyariyders.driver.presentation.biodata.BioDataViewModel
import com.oyariyders.driver.presentation.brandpage.BrandPage
import com.oyariyders.driver.presentation.emailotp.EmailOtp
import com.oyariyders.driver.presentation.emailotp.EmailOtpViewModel
import com.oyariyders.driver.presentation.loginemail.LoginEmailPage
import com.oyariyders.driver.presentation.loginphone.LoginPhone
import com.oyariyders.driver.presentation.loginphone.LoginPhoneViewModel
import com.oyariyders.driver.presentation.onboarding.Onboarding
import com.oyariyders.driver.presentation.otp.OTPScreenViewModel
import com.oyariyders.driver.presentation.otp.OtpScreen
import com.oyariyders.driver.presentation.phonenumberoptions.PhoneNumberOptions
import com.oyariyders.driver.presentation.phonenumberoptions.PhoneNumberOptionsViewModel
import com.oyariyders.driver.repository.DriverRepository
import com.oyariyders.driver.retrofit.InitializeRetrofit
import com.oyariyders.driver.ui.theme.DriverTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.security.SecureRandom
import java.util.Base64
import kotlin.getValue
import kotlin.time.Duration.Companion.seconds

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
            .apply {
                setKeepOnScreenCondition {
                    viewModel.isLoading.value
                }
            }
        super.onCreate(savedInstanceState)
        val webClientId = "385918954773-5pr7pj21nh6kp5vvkkcmane6a40b3a16.apps.googleusercontent.com"
        enableEdgeToEdge()
        setContent {
            DriverTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "BrandPage",
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None }
                    ){
                        composable(
                            route = "LoginPhone",
                            enterTransition = {
                                fadeIn(
                                    animationSpec = tween(
                                        300, easing = LinearEasing
                                    )
                                ) + slideIntoContainer(
                                    animationSpec = tween(300, easing = EaseIn),
                                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                                )
                            },
                            exitTransition = {
                                fadeOut(
                                    animationSpec = tween(
                                        300, easing = LinearEasing
                                    )
                                ) + slideOutOfContainer(
                                    animationSpec = tween(300, easing = EaseOut),
                                    towards = AnimatedContentTransitionScope.SlideDirection.End
                                )
                            }
                        ) {
                            val phoneLoginViewModel = viewModel<LoginPhoneViewModel>()
                            LoginPhone(navController, phoneLoginViewModel)
                        }
                        composable(
                            route = "BrandPage",
                            enterTransition = {
                                fadeIn(
                                    animationSpec = tween(
                                        300, easing = LinearEasing
                                    )
                                ) + slideIntoContainer(
                                    animationSpec = tween(300, easing = EaseIn),
                                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                                )
                            },
                            exitTransition = {
                                fadeOut(
                                    animationSpec = tween(
                                        300, easing = LinearEasing
                                    )
                                ) + slideOutOfContainer(
                                    animationSpec = tween(300, easing = EaseOut),
                                    towards = AnimatedContentTransitionScope.SlideDirection.End
                                )
                            }
                        ) {
                            //val phoneLoginViewModel = viewModel<LoginPhoneViewModel>()
                            BrandPage(navController)
                        }
                        composable(
                            route = "PhoneNumberOptions?fullPhoneNumber={fullPhoneNumber}",
                            arguments = listOf(navArgument( name = "fullPhoneNumber") {
                                type = NavType.StringType
                                nullable = true
                                defaultValue = null
                            })){ backStackEntry ->

                            val fullPhoneNumber = backStackEntry.arguments?.getString("fullPhoneNumber")
                            val repository = DriverRepository(InitializeRetrofit.driverApi)

                            val phoneLoginViewModel = viewModel<LoginPhoneViewModel>()
                            val phoneVM = viewModel<PhoneNumberOptionsViewModel>(){
                                PhoneNumberOptionsViewModel(repository)
                            }
                            PhoneNumberOptions(
                                navController,
                                phoneVM,
                                phoneLoginViewModel,
                                phoneNumber = fullPhoneNumber
                            )
                        }
                        composable(
                            route = "OtpScreen/fullPhoneNumber={fullPhoneNumber}",
                            arguments = listOf(navArgument( name = "fullPhoneNumber") {
                                type = NavType.StringType
                                nullable = true
                                defaultValue = null
                            })){ backStackEntry ->

                            val fullPhoneNumber = backStackEntry.arguments?.getString("fullPhoneNumber")
                            val repository = DriverRepository(InitializeRetrofit.driverApi)

                            val otpScreenOptionsViewModel = viewModel<OTPScreenViewModel>()
                            val signUpViewModel = viewModel<SignUpViewModel>(){
                                SignUpViewModel(repository)
                            }
                            OtpScreen(
                                navController,
                                phoneNumber = fullPhoneNumber,
                                screenModel = signUpViewModel,
                                viewModel = otpScreenOptionsViewModel
                            )
                        }
                        composable(route = "LoginEmailPage") {
                            //val phoneLoginViewModel = viewModel<LoginPhoneViewModel>()
                            LoginEmailPage(navController, webClientId)
                        }
                        composable(
                            route = "EmailOtp/fullPhoneNumber={fullPhoneNumber}",
                            arguments = listOf(navArgument( name = "fullPhoneNumber") {
                                type = NavType.StringType
                                nullable = true
                                defaultValue = null
                            })) { backStackEntry ->

                            val fullPhoneNumber = backStackEntry.arguments?.getString("fullPhoneNumber")
                            val repository = DriverRepository(InitializeRetrofit.driverApi)
                            val emailOtpViewModel = viewModel<EmailOtpViewModel>()
                            val signUpViewModel = viewModel<SignUpViewModel>(){
                                SignUpViewModel(repository)
                            }
                            EmailOtp(
                                navController,
                                screenModel = signUpViewModel,
                                viewModel = emailOtpViewModel,
                                email = fullPhoneNumber
                            )
                        }
                        composable(route = "BioData") {
                            val repository = DriverRepository(InitializeRetrofit.driverApi)
                            val biodataVM = viewModel<BioDataViewModel>(){
                                BioDataViewModel(repository)
                            }
                            BioData(navController, biodataVM)
                        }
                        composable(route = "Onboarding") {
                            //val repository = DriverRepository(InitializeRetrofit.driverApi)
//                            val biodataVM = viewModel<BioDataViewModel>(){
//                                BioDataViewModel(repository)
//                            }
                            Onboarding(navController)
                        }
                    }
                }
            }
        }
    }
}

class MainViewModel(): ViewModel (){
    private var _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    // State to hold the phone number hint
    private val _phoneNumber = MutableStateFlow<String?>(null)
    val phoneNumber = _phoneNumber.asStateFlow()

    init {
        viewModelScope.launch {
            delay(4.seconds)
            _isLoading.value = false
        }
    }

    fun onPhoneNumberHintReceived(number: String?) {
        _phoneNumber.value = number
    }

    // --- FIX 1: Add a function to handle phone number changes ---
    fun onPhoneNumberChange(newNumber: String) {
        _phoneNumber.value = newNumber
    }
}

//This code will not work on Android versions < UPSIDE_DOWN_CAKE when GetCredentialException is
//is thrown.
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
suspend fun signIn(request: GetCredentialRequest, context: Context, navController: NavController): Exception? {
    val credentialManager = CredentialManager.create(context)
    val failureMessage = "Sign in failed!"
    var e: Exception? = null
    //using delay() here helps prevent NoCredentialException when the BottomSheet Flow is triggered
    //on the initial running of our app
    delay(250)
    try {
        // The getCredential is called to request a credential from Credential Manager.
        val result = credentialManager.getCredential(
            request = request,
            context = context,
        )
        Log.i(TAG, result.toString())
        Toast.makeText(context, "Sign in successful!", Toast.LENGTH_SHORT).show()
        navController.navigate("BioData")
        Log.i(TAG, "(☞ﾟヮﾟ)☞  Sign in Successful!  ☜(ﾟヮﾟ☜)")

    } catch (e: GetCredentialException) {
        Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show()
        Log.e(TAG, "$failureMessage: Failure getting credentials", e)

    } catch (e: GoogleIdTokenParsingException) {
        Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show()
        Log.e(TAG, "$failureMessage: Issue with parsing received GoogleIdToken", e)

    } catch (e: NoCredentialException) {
        Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show()
        Log.e(TAG, "$failureMessage: No credentials found", e)
        return e

    } catch (e: GetCredentialCustomException) {
        Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show()
        Log.e(TAG, "$failureMessage: Issue with custom credential request", e)

    } catch (e: GetCredentialCancellationException) {
        Toast.makeText(context, ": Sign-in cancelled", Toast.LENGTH_SHORT).show()
        Log.e(TAG, "$failureMessage: Sign-in was cancelled", e)
    }
    return e
}

//This function is used to generate a secure nonce to pass in with our request
@RequiresApi(Build.VERSION_CODES.O)
fun generateSecureRandomNonce(byteLength: Int = 32): String {
    val randomBytes = ByteArray(byteLength)
    SecureRandom.getInstanceStrong().nextBytes(randomBytes)
    return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes)
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DriverTheme {
        Greeting("Android")
    }
}