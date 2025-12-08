package com.oyariyders.driver.presentation.welcome

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.oyariyders.driver.R
import com.oyariyders.driver.presentation.onboarding.adaptiveBackgroundProvider
import com.oyariyders.driver.ui.theme.DarkBrownTaupe
import com.oyariyders.driver.ui.theme.MediumGray
import com.oyariyders.driver.ui.theme.PalePeach
import com.oyariyders.driver.ui.theme.PlusJakartaSansFontFamily
import com.oyariyders.driver.ui.theme.SlateGray
import com.oyariyders.driver.ui.theme.White10
import com.oyariyders.driver.ui.theme.almostBlack
import com.oyariyders.driver.ui.theme.almostBlackGray
import com.oyariyders.driver.ui.theme.deepBlueEnd
import com.oyariyders.driver.ui.theme.lightBlueStart
import com.oyariyders.driver.ui.theme.vividOrange
import com.oyariyders.driver.utils.AdaptiveBackground
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Welcome(navController: NavController){
    val background = adaptiveBackground()
    val borderBg = getAdaptiveBorder()
    val scope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    val image = painterResource(R.drawable.rectangle)

    val myVerticalGradient = Brush.linearGradient(
        colors = listOf(
            lightBlueStart,
            deepBlueEnd
        ),
        // Define the start point (0%) at the bottom and the end point (100%) at the top
        start = Offset(x = 0f, y = Float.POSITIVE_INFINITY),
        end = Offset(x = 0f, y = 0f)
    )

    val stops = listOf(
        -0.136f, // Start position
        0.9262f  // End position
    )


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
                            .background(Color(0xFFE3E5F0), shape = RoundedCornerShape(16.dp))
                    ) {
                        Image(
                            painter = painterResource(background.resourceId),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds,
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Welcome, Ibrahim",
                                color = Color.Black,
                                fontFamily = PlusJakartaSansFontFamily,
                                //modifier = Modifier.align(Alignment.CenterStart),
                                textAlign = TextAlign.Center,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Complete the setup below to start earning",
                                color = Color.Black,
                                fontFamily = PlusJakartaSansFontFamily,
                                //modifier = Modifier.align(Alignment.Center),
                                fontWeight = FontWeight.Medium
                            )
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface,
                                ),
                                modifier = Modifier
                                    .fillMaxWidth(0.6f)
                                    .fillMaxHeight(0.35f)
                                    .padding(12.dp),
                                //.align(Alignment.BottomStart)
                                shape = RoundedCornerShape(8.dp),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Text(
                                        text = "Your ID number",
                                        fontSize = 12.sp,
                                        color = Color(0xFF777777)
                                    )
                                    Text(
                                        text = "RTY8734",
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Icon(
                                        painter = painterResource(R.drawable.copy),
                                        contentDescription = "Copy Icon"
                                    )
                                }
                            }
                        }
                    }
                }
                is AdaptiveBackground.SolidColor -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(background.color)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Welcome, Ibrahim",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontFamily = PlusJakartaSansFontFamily,
                                //modifier = Modifier.align(Alignment.CenterStart),
                                textAlign = TextAlign.Center,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Complete the setup below to start earning",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontFamily = PlusJakartaSansFontFamily,
                                //modifier = Modifier.align(Alignment.Center),
                                fontWeight = FontWeight.Medium
                            )
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface,
                                ),
                                modifier = Modifier
                                    .fillMaxWidth(0.6f)
                                    .fillMaxHeight(0.35f)
                                    .padding(12.dp),
                                //.align(Alignment.BottomStart)
                                shape = RoundedCornerShape(8.dp),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Text(
                                        text = "Your ID number",
                                        fontSize = 12.sp,
                                        color = Color(0xFF777777)
                                    )
                                    Text(
                                        text = "RTY8734",
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Icon(
                                        painter = painterResource(R.drawable.copy),
                                        contentDescription = "Copy Icon"
                                    )
                                }
                            }
                        }
                    }
                }

                is AdaptiveBackground.SolidBrush -> TODO()
            }
            Spacer(Modifier.height(8.dp))
            when(borderBg){
                is AdaptiveBackground.ImageResource -> TODO()
                is AdaptiveBackground.SolidBrush -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .background(borderBg.brush, shape = CircleShape),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.inverseSurface,
                                    shape = CircleShape
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center // Center the text within the box
                        ) {
                            Text(
                                text = "1",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.inverseOnSurface
                            )
                        }
                        Box (
                            modifier = Modifier
                                .background(color = Color.Black, shape = CircleShape)
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center // Center the text within the box
                        ){
                            Text(
                                text = "2",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                        Box (
                            modifier = Modifier
                                .background(color = Color.Black, shape = CircleShape)
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center // Center the text within the box
                        ){
                            Text(
                                text = "3",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                        Box(
                            modifier = Modifier
                                .background(color = Color.Black, shape = CircleShape)
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center // Center the text within the box
                        ) {
                            Text(
                                text = "4",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                        Box(
                            modifier = Modifier
                                .background(color = Color.Black, shape = CircleShape)
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center // Center the text within the box
                        ){
                            Text(
                                text = "5",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                        Box(
                            modifier = Modifier
                                .background(color = Color.Black, shape = CircleShape)
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center // Center the text within the box
                        ) {
                            Text(
                                text = "6",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                    }
                }
                is AdaptiveBackground.SolidColor -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .background(borderBg.color, shape = CircleShape),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = MediumGray,
                                    shape = CircleShape
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center // Center the text within the box
                        ) {
                            Text(
                                text = "1",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Box (
                            modifier = Modifier
                                //.background(color = Color.Black, shape = CircleShape)
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center // Center the text within the box
                        ){
                            Text(
                                text = "2",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Box (
                            modifier = Modifier
                                //.background(color = Color.Black, shape = CircleShape)
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center // Center the text within the box
                        ){
                            Text(
                                text = "3",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Box(
                            modifier = Modifier
                                //.background(color = Color.Black, shape = CircleShape)
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center // Center the text within the box
                        ) {
                            Text(
                                text = "4",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Box(
                            modifier = Modifier
                                //.background(color = Color.Black, shape = CircleShape)
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center // Center the text within the box
                        ){
                            Text(
                                text = "5",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Box(
                            modifier = Modifier
                                //.background(color = Color.Black, shape = CircleShape)
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center // Center the text within the box
                        ) {
                            Text(
                                text = "6",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
            ){
              Row(
                  modifier = Modifier
                      .fillMaxSize(),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.SpaceEvenly
              ) {
                  Icon(
                      painter = painterResource(R.drawable.location_add),
                      contentDescription = "Location Add",
                      tint = getAdaptiveIconColor(),
                      modifier = Modifier
                          .border(1.dp, PalePeach, CircleShape)
                          .padding(12.dp)
                  )
                  Column{
                      Text(
                          "Location",
                          color = MaterialTheme.colorScheme.inverseSurface,
                          textAlign = TextAlign.Center,
                          fontFamily = PlusJakartaSansFontFamily,
                          fontSize = 16.sp,
                      )
                      Text(
                          "Choose the city where you’ll be driving and earning.",
                          color = getAdaptiveTextColor(),
                          textAlign = TextAlign.Center,
                          fontFamily = PlusJakartaSansFontFamily,
                          modifier = Modifier.padding(4.dp),
                          fontSize = 12.sp,
                          fontWeight = FontWeight.Medium
                      )
                  }
                  Icon(
                      painter = painterResource(R.drawable.arrow_right),
                      contentDescription = "Arrow Right Icon"
                  )
              }
            }
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable(enabled = true, onClick = {
                        navController.navigate("PhotoUpload")
                    }),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(R.drawable.profile_tick),
                    tint = getAdaptiveTintColor(),
                    contentDescription = "Add Picture",
                    modifier = Modifier
                        //.clip(CircleShape)
                        .shadow(1.dp, shape = CircleShape)
                        .border(1.dp, White10, CircleShape)
                        .padding(12.dp)
                )
                Text(
                    "Add a profile picture",
                    color = MaterialTheme.colorScheme.inverseSurface,
                    fontFamily = PlusJakartaSansFontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    painter = painterResource(R.drawable.arrow_right),
                    contentDescription = "Arrow Right Icon"
                )
            }
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(R.drawable.driverlicense),
                    contentDescription = "Add Picture",
                    tint = getAdaptiveTintColor(),
                    modifier = Modifier
                        .shadow(1.dp, shape = CircleShape)
                        .border(1.dp, White10, CircleShape)
                        .padding(12.dp)
                )
                Text(
                    "Nigeria Driver’s License",
                    color = MaterialTheme.colorScheme.inverseSurface,
                    fontFamily = PlusJakartaSansFontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    painter = painterResource(R.drawable.arrow_right),
                    contentDescription = "Arrow Right Icon"
                )
            }
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable(enabled = true, onClick = {
                        navController.navigate("SearchVehicle")
                    }),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(R.drawable.car_icon),
                    contentDescription = "Add Picture",
                    tint = getAdaptiveTintColor(),
                    modifier = Modifier
                        .shadow(1.dp, shape = CircleShape)
                        .border(1.dp, White10, CircleShape)
                        .padding(12.dp)
                )
                Text(
                    "Select your Vehicle type",
                    color = MaterialTheme.colorScheme.inverseSurface,
                    fontFamily = PlusJakartaSansFontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    painter = painterResource(R.drawable.arrow_right),
                    contentDescription = "Arrow Right Icon"
                )
            }
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(R.drawable.document_text),
                    contentDescription = "Add Picture",
                    tint = getAdaptiveTintColor(),
                    modifier = Modifier
                        .shadow(1.dp, shape = CircleShape)
                        .border(1.dp, White10, CircleShape)
                        .padding(12.dp)
                )
                Text(
                    "Vehicle Insurance Policy",
                    color = MaterialTheme.colorScheme.inverseSurface,
                    fontFamily = PlusJakartaSansFontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    painter = painterResource(R.drawable.arrow_right),
                    contentDescription = "Arrow Right Icon"
                )
            }
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(R.drawable.document_text),
                    contentDescription = "Add Picture",
                    tint = getAdaptiveTintColor(),
                    modifier = Modifier
                        .shadow(1.dp, shape = CircleShape)
                        .border(1.dp, White10, CircleShape)
                        .padding(12.dp)
                )
                Text(
                    "Vehicle Inspection Report",
                    color = MaterialTheme.colorScheme.inverseSurface,
                    fontFamily = PlusJakartaSansFontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    painter = painterResource(R.drawable.arrow_right),
                    contentDescription = "Arrow Right Icon"
                )
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
                            navController.navigate("Location")
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
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
                    }
                }
            }
        }
    }
}

@Composable
fun getAdaptiveTextColor(): Color {
    // isSystemInDarkTheme() returns true if the system's dark theme is currently active.
    return if (isSystemInDarkTheme()) {
        Color.White // Lighter color for better contrast in Dark Mode
    } else {
        Color(0xFF6D6D6D) // Original color for Light Mode
    }
}

@Composable
fun getAdaptiveIconColor(): Color {
    // isSystemInDarkTheme() returns true if the system's dark theme is currently active.
    return if (isSystemInDarkTheme()) {
        vividOrange // Lighter color for better contrast in Dark Mode
    } else {
        DarkBrownTaupe // Original color for Light Mode
    }
}
@Composable
fun getAdaptiveTintColor(): Color {
    // isSystemInDarkTheme() returns true if the system's dark theme is currently active.
    return if (isSystemInDarkTheme()) {
        SlateGray // Lighter color for better contrast in Dark Mode
    } else {
        Color.Black // Original color for Light Mode
    }
}

@Composable
fun adaptiveBackground(): AdaptiveBackground {
    return if (isSystemInDarkTheme()) {
        // Dark Mode: Return an almost transparent background (fully transparent Black in this case)
        // If you wanted 'almost' transparent, you could use Color(0x22000000) for 13.7% opacity black
        AdaptiveBackground.SolidColor(Color.Transparent)
    } else {
        // Light Mode: Return the image resource ID
        AdaptiveBackground.ImageResource(R.drawable.rectangle)
    }
}

@Composable
fun getAdaptiveBorder(): AdaptiveBackground {
    val myVerticalGradient = Brush.linearGradient(
        colors = listOf(
            lightBlueStart,
            deepBlueEnd
        ),
        // Define the start point (0%) at the bottom and the end point (100%) at the top
        start = Offset(x = 0f, y = Float.POSITIVE_INFINITY),
        end = Offset(x = 0f, y = 0f)
    )

    return if (isSystemInDarkTheme()) {
        // Dark Mode: Return an almost transparent background (fully transparent Black in this case)
        // If you wanted 'almost' transparent, you could use Color(0x22000000) for 13.7% opacity black
        AdaptiveBackground.SolidColor(almostBlackGray)
    } else {
        // Light Mode:
        AdaptiveBackground.SolidBrush(myVerticalGradient)
    }
}




@Composable
fun SelectedCard(
    @DrawableRes rowIcon: Int,
    @DrawableRes iconRight: Int,
    title: String,
    subtitle: String
){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
    ){
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                painter = painterResource(rowIcon),
                contentDescription = "Location Add",
                modifier = Modifier
                    .border(1.dp, PalePeach, CircleShape)
                    .padding(12.dp)
            )
            Column{
                Text(
                    "Location",
                    color = MaterialTheme.colorScheme.inverseSurface,
                    textAlign = TextAlign.Center,
                    fontFamily = PlusJakartaSansFontFamily,
                    fontSize = 16.sp,
                )
                Text(
                    "Choose the city where you’ll be driving and earning.",
                    color = getAdaptiveTextColor(),
                    textAlign = TextAlign.Center,
                    fontFamily = PlusJakartaSansFontFamily,
                    modifier = Modifier.padding(4.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Icon(
                painter = painterResource(iconRight),
                contentDescription = "Arrow Right Icon"
            )
        }
    }
}
