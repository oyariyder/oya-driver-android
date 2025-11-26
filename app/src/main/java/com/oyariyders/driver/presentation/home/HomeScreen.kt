package com.oyariyders.driver.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.oyariyders.driver.ui.theme.PlusJakartaSansFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){
    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    val abuja = LatLng(9.0765, 7.3986)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(abuja, 12f)
    }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(true) }
    val semiTransparentGrayFromHex = Color(0x80F5F5F5)
    val colorStops = arrayOf(
        0.2386f to Color(0xFF516486), // 23.86%
        0.9169f to Color(0xFFFFFFFF)  // 91.69%
    )
    // 2. Create the vertical Brush
    val myBrush = Brush.verticalGradient(colorStops = colorStops)
    // Define the two colors using the hex codes
    val startColor = Color.White
    val endColor = Color.Transparent // #FFFFFF is equivalent to Color.White
    // Create a vertical linear gradient brush
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(startColor, endColor)
    )
    val start = Color("#516486".toColorInt())
    val end = Color.White
    val weatherBackgroundGradient = Brush.verticalGradient(
        colors = listOf(start, end)
    )
    // Define the colors and their positions (normalized coordinates from 0f to 1f)
    val newGradientBrush = Brush.linearGradient(
        // Let's use the colors and stop positions provided first:
        // 🎨 Use 'to' to create the Pair<Float, Color> for each stop
        0.0594f to Color(0xFFFFFFFF), // Stop 1: 5.94% -> White
        0.2197f to Color(0xFFF0F1F1),  // Stop 2: 21.97% -> Light Gray
        start = Offset(x = 0f, y = 0f), // Top-Left
        end = Offset(x = 0f, y = Float.POSITIVE_INFINITY)
    )
    // Define a shape with a good amount of rounding (e.g., 16dp on all corners)
    val roundedTopShape = RoundedCornerShape(
        topStart = 24.dp, // Fully rounded top-left corner
        topEnd = 24.dp,   // Fully rounded top-right corner
        bottomStart = 24.dp, // Flat bottom-left corner
        bottomEnd = 24.dp    // Flat bottom-right corner
    )
    val roundedShape = RoundedCornerShape(16.dp)
    val circleShape = CircleShape
    val stadiumShape = RoundedCornerShape(32.dp)
    // Assuming you have an image in res/drawable named 'sheet_background'
    //val backgroundPainter: Painter = painterResource(id = R.drawable.sheet_background)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        contentPadding ->
        Box(Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.matchParentSize(),
                properties = properties,
                uiSettings = uiSettings
            )
            Switch(
                checked = uiSettings.zoomControlsEnabled,
                onCheckedChange = {
                    uiSettings = uiSettings.copy(zoomControlsEnabled = it)
                }
            )
        }
   }
}