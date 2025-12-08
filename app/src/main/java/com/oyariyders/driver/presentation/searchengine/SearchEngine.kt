package com.oyariyders.driver.presentation.searchengine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.oyariyders.driver.presentation.location.getAdaptiveDividerColor
import com.oyariyders.driver.presentation.selectvechicle.SimpleSearchBar
import com.oyariyders.driver.ui.theme.PlusJakartaSansFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchEngine(navController: NavController){
    val scope = rememberCoroutineScope()
    var selected by remember { mutableStateOf(true) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    var expanded by rememberSaveable { mutableStateOf(false) }
    // Create and remember the text field state
    val textFieldState = rememberTextFieldState()
    val items = listOf(
        "Mercedes Benz", "Toyota Camry", "Honda Accord",
    )

    // Filter items based on the current search text
    val filteredItems by remember {
        derivedStateOf {
            val searchText = textFieldState.text.toString()
            if (searchText.isEmpty()) {
                emptyList()
            } else {
                items.filter { it.contains(searchText, ignoreCase = true) }
            }
        }
    }
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
                "Pick the type of vehicle you’ll use for rides",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
                fontFamily = PlusJakartaSansFontFamily,
                fontSize = 22.sp
            )
            Spacer(Modifier.height(12.dp))
            SimpleSearchBar(
                textFieldState = textFieldState,
                onSearch = { /* Handle search submission */ },
                placeholder = { Text("Enter your engine number")},
                searchResults = filteredItems
            )
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
            ){
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Mercedes Benz",
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 16.sp,
                    )
                }
                HorizontalDivider(
                    color = getAdaptiveDividerColor(),
                    thickness = 1.dp,
                    modifier = Modifier.padding(4.dp)
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Toyota Camry",
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 16.sp,
                    )
                }
                HorizontalDivider(
                    color = getAdaptiveDividerColor(),
                    thickness = 1.dp,
                    modifier = Modifier.padding(4.dp)
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Honda Accord",
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 16.sp,
                    )
                }
            }
        }
    }
}