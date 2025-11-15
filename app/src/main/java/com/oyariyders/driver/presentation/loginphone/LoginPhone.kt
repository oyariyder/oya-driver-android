package com.oyariyders.driver.presentation.loginphone

import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.oyariyders.driver.R
import com.oyariyders.driver.ui.theme.PlusJakartaSansFontFamily
import com.oyariyders.driver.ui.theme.deepBlue90
import com.oyariyders.driver.ui.theme.grayShade
import com.simon.xmaterialccp.component.MaterialCountryCodePicker
import com.simon.xmaterialccp.data.ccpDefaultColors
import com.simon.xmaterialccp.data.utils.checkPhoneNumber
import com.simon.xmaterialccp.data.utils.getDefaultLangCode
import com.simon.xmaterialccp.data.utils.getDefaultPhoneCode
import com.simon.xmaterialccp.data.utils.getLibCountries
import kotlinx.coroutines.launch

@Composable
fun LoginPhone(
    navController: NavController,
    viewModel: LoginPhoneViewModel
){
    val image = painterResource(R.drawable.driver_bg)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize(),
        ){
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
            WideArea(
                navController = navController,
                introText =  "The easiest way to earn as you go",
                headerText = "Enter your phone number to continue",
                route = "PhoneNumberOptions",
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun WideArea(
    modifier: Modifier = Modifier,
    viewModel: LoginPhoneViewModel,
    navController: NavController,
    introText: String,
    headerText: String,
    route: String = "LoginEmailPage"
) {
    val scrollState = rememberScrollState()
    val semiTransparentGrayFromHex = Color(0x9A9A9A80)
    // You can now observe the phone number state from the ViewModel
    val White30PercentOpacity = Color(
        red = 1.0f,     // 255 / 255 = 1.0f
        green = 1.0f,   // 255 / 255 = 1.0f
        blue = 1.0f,    // 255 / 255 = 1.0f
        alpha = 0.3f    // Directly from the rgba value
    )

    val transparentBackground = Color(
        red = 0.45f,  // 154 / 255
        green = 0.45f, // 154 / 255
        blue = 0.45f,  // 154 / 255
        alpha = 0.75f     // Direct alpha value (50% opaque)
    )



    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Spacer(Modifier.height(80.dp))
        Text(
            text = introText,
            fontWeight = FontWeight.Bold,
            fontFamily = PlusJakartaSansFontFamily,
            textAlign = TextAlign.Center,
            lineHeight = 40.sp,
            fontSize = 44.sp,
            color = colorResource(R.color.white),
            modifier = Modifier.padding(8.dp)
        )
        Card(
            colors = CardDefaults.cardColors(
                containerColor = transparentBackground,
            ),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.4f),
            shape = RoundedCornerShape(8)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = headerText,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.white),
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    SelectCountryWithCountryCode(viewModel = viewModel)
                    Button(
                        onClick = {
                            navController.navigate(route + "?fullPhoneNumber=${viewModel.phoneNumber.value.toString()}")
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16), // Set the shape to RectangleShape
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black, // Set the button's background color
                            contentColor = Color.White // Set the color for the content (Icon and Text)
                        ),
                        contentPadding = PaddingValues(all = 12.dp)
                    ) {
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
                Spacer(Modifier.height(24.dp))
                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        "By continuing you agree  to Diver’s Privacy Policy and Term of Use",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontFamily = PlusJakartaSansFontFamily
                    )
                }
            }
        }
    }
}

@Composable
fun SelectCountryWithCountryCode(
    collectedNumber: String? = null,
    viewModel: LoginPhoneViewModel
) {
    val context = LocalContext.current
    var phoneCode by remember { mutableStateOf(getDefaultPhoneCode(context)) }
    val phoneNumber = rememberSaveable { mutableStateOf( collectedNumber ?: "") }
    var defaultLang by rememberSaveable { mutableStateOf(getDefaultLangCode(context)) }
    var isValidPhone by remember { mutableStateOf(true) }
    val fieldOutlineColor = Color(
        red = 0.937f,
        green = 0.937f,
        blue = 0.937f,
        alpha = 0.9f // Directly setting the 90% opacity
    )

    Column(
        modifier = Modifier.padding(12.dp)
    ) {

        MaterialCountryCodePicker(
            pickedCountry = {
                phoneCode = it.countryPhoneCode
                defaultLang = it.countryCode
            },
            defaultCountry = getLibCountries().single { it.countryCode == defaultLang },
            error = !isValidPhone,
            dropDownIcon = R.drawable.arrow_down,
            countryItemHorizontalPadding = 4.dp,
            text = phoneNumber.value,
            onValueChange = { newNumber: String ->
                phoneNumber.value = newNumber
                viewModel.onPhoneNumberChange(newNumber)
                isValidPhone = checkPhoneNumber(
                    phone = newNumber,
                    fullPhoneNumber = "$phoneCode$newNumber",
                    countryCode = defaultLang
                )
                viewModel.toggleButtonState(isValidPhone)
            }, // Call the event handler
            phonehintnumbertextstyle = MaterialTheme.typography.bodyMedium.copy(color = deepBlue90),
            searchFieldPlaceHolderTextStyle = MaterialTheme.typography.bodyMedium,
            searchFieldTextStyle = MaterialTheme.typography.bodyMedium,
            phonenumbertextstyle = MaterialTheme.typography.bodyMedium.copy(color = deepBlue90),
            countrytextstyle = MaterialTheme.typography.bodyMedium,
            countrycodetextstyle = MaterialTheme.typography.bodyMedium.copy(color = deepBlue90),
            showErrorText = true,
            showCountryCodeInDIalog = true,
            showDropDownAfterFlag = true,
            textFieldShapeCornerRadiusInPercentage = 20,
            searchFieldShapeCornerRadiusInPercentage = 40,
            appbartitleStyle = MaterialTheme.typography.titleLarge,
            countryItemBgShape = RoundedCornerShape(12.dp),
            showCountryFlag = true,
            showCountryCode = true,
            isEnabled = true,
            colors = ccpDefaultColors(
                primaryColor = MaterialTheme.colorScheme.primary,
                errorColor = MaterialTheme.colorScheme.error,
                backgroundColor = Color.White,
                surfaceColor = MaterialTheme.colorScheme.surface,
                outlineColor = fieldOutlineColor,
                disabledOutlineColor = MaterialTheme.colorScheme.outline.copy(0.1f),
                unfocusedOutlineColor = fieldOutlineColor,
                textColor = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                cursorColor = MaterialTheme.colorScheme.primary,
                topAppBarColor = MaterialTheme.colorScheme.surface,
                countryItemBgColor = MaterialTheme.colorScheme.surface,
                searchFieldBgColor = MaterialTheme.colorScheme.surface,
                dialogNavIconColor = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                dropDownIconTint = deepBlue90

            )
        )

        val fullPhoneNumber = "$phoneCode${phoneNumber}"
        val checkPhoneNumber = checkPhoneNumber(
            phone = phoneNumber.value,
            fullPhoneNumber = fullPhoneNumber,
            countryCode = defaultLang
        )
    }
}