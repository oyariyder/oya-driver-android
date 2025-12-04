package com.oyariyders.driver.presentation.location

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.oyariyders.driver.R
import com.oyariyders.driver.presentation.phonenumberoptions.PhoneNumberOptionsEvent
import com.oyariyders.driver.ui.theme.DarkGray42
import com.oyariyders.driver.ui.theme.PalePeach
import com.oyariyders.driver.ui.theme.PlusJakartaSansFontFamily
import com.oyariyders.driver.ui.theme.PrimaryGreen
import com.oyariyders.driver.ui.theme.SlateGray
import com.oyariyders.driver.ui.theme.VeryLightGray
import com.oyariyders.driver.utils.RadioOption
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Location(navController: NavController){
    val scope = rememberCoroutineScope()
    var selected by remember { mutableStateOf(true) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    val locationList = listOf("Lagos", "Abuja", "Ibadan", "Ogun", "Kaduna")
    // State for Location List
    val radioOptionsList = listOf<RadioOption>(
        RadioOption("Lagos"),
        RadioOption("Abuja"),
        RadioOption("Ibadan"),
        RadioOption("Ogun"),
        RadioOption("Kaduna"),
    )

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptionsList[0].location) }

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Select the location you want to drive in",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
                fontFamily = PlusJakartaSansFontFamily,
                fontSize = 22.sp
            )
            Spacer(Modifier.height(12.dp))
            //DropdownMenuWithDetails()
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(335.dp)
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
            ){
                RadioButtonSingleSelection(
                    radioOptionsList = radioOptionsList,
                    selectedOption = selectedOption,
                    onOptionSelected = onOptionSelected
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement =  Arrangement.Center
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                ,
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
                        "Done",
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )
                }
            }
        }
    }
}




@Composable
fun RadioButtonSingleSelection(modifier: Modifier = Modifier,
                               radioOptionsList: List<RadioOption>,
                               selectedOption: String,
                               onOptionSelected: (String) -> Unit
) {
    // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    Column(
        modifier.selectableGroup(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        radioOptionsList.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text.location == selectedOption),
                        onClick = { onOptionSelected(text.location) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = text.location,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    fontFamily = PlusJakartaSansFontFamily,
                    fontSize = 16.sp,
                )
                RadioButton(
                    selected = (text.location == selectedOption),
                    onClick = null // null recommended for accessibility with screen readers
                )
            }
            HorizontalDivider(
                color = getAdaptiveDividerColor(),
                thickness = 1.dp,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
fun getAdaptiveDividerColor(): Color {
    // isSystemInDarkTheme() returns true if the system's dark theme is currently active.
    return if (isSystemInDarkTheme()) {
        DarkGray42 // Lighter color for better contrast in Dark Mode
    } else {
        VeryLightGray // Original color for Light Mode
    }
}