package com.oyariyders.driver.presentation.welcome

import androidx.compose.foundation.Image
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
import com.oyariyders.driver.ui.theme.PlusJakartaSansFontFamily
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Welcome(navController: NavController){

    val scope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    val image = painterResource(R.drawable.rectangle)

    val lightBlueStart = Color(0xFFD1E2FB) // 0.05%
    val deepBlueEnd = Color(0xFF0D56FF)

    val MyVerticalGradient = Brush.linearGradient(
        colors = listOf(
            lightBlueStart,
            deepBlueEnd
        ),
        // Define the start point (0%) at the bottom and the end point (100%) at the top
        start = Offset(x = 0f, y = Float.POSITIVE_INFINITY),
        end = Offset(x = 0f, y = 0f)
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFE3E5F0), shape = RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = image,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                )
                Text(
                    "Welcome, Ibrahim",
                    color = Color.Black,
                    fontFamily = PlusJakartaSansFontFamily,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Complete the setup below to start earning",
                    color = Color.Black,
                    fontFamily = PlusJakartaSansFontFamily,
                    modifier = Modifier.align(Alignment.BottomCenter),
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(Modifier.height(8.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.15f)
                    .padding(12.dp),
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
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .background(MyVerticalGradient, shape = CircleShape),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.inverseOnSurface, shape = CircleShape)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center // Center the text within the box
                ) {
                    Text(
                        text = "1",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.inverseSurface
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
            Spacer(Modifier.height(8.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
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
                      contentDescription = "Location Add"
                  )
                  Column{
                      Text(
                          "Location",
                          color = Color.Black,
                          fontFamily = PlusJakartaSansFontFamily,
                          fontSize = 18.sp,
                      )
                      Text(
                          "Choose the city where you’ll be driving and earning.",
                          color = Color(0xFF6D6D6D),
                          fontFamily = PlusJakartaSansFontFamily,
                          fontSize = 14.sp,
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
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(R.drawable.profile_tick),
                    contentDescription = "Add Picture"
                )
                Text(
                    "Add a profile picture",
                    color = Color.Black,
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(R.drawable.driverlicense),
                    contentDescription = "Add Picture"
                )
                Text(
                    "Nigeria Driver’s License",
                    color = Color.Black,
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(R.drawable.car_icon),
                    contentDescription = "Add Picture"
                )
                Text(
                    "Select your Vehicle type",
                    color = Color.Black,
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(R.drawable.document_text),
                    contentDescription = "Add Picture"
                )
                Text(
                    "Vehicle Insurance Policy",
                    color = Color.Black,
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(R.drawable.document_text),
                    contentDescription = "Add Picture"
                )
                Text(
                    "Vehicle Inspection Report",
                    color = Color.Black,
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
                            navController.navigate("OtpScreen/fullPhoneNumber=${email}")
                        }

                    },
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
                        Text("Continue")
                    }
                }
            }
        }
    }
}
