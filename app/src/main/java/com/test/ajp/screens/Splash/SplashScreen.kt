package com.test.ajp.screens.Splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.test.ajp.R
import com.test.ajp.ui.theme.primaryContainerLight
import com.test.ajp.ui.theme.primaryDarkHighContrast
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(gotoHome: () ->Unit) {
    SplashScreenSkeleton (gotoHome = gotoHome)
}

@Composable
fun SplashScreenSkeleton(gotoHome: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        gotoHome()
    }
    SplashScreenSkeleton()
}

@Composable
fun SplashScreenSkeleton(modifier: Modifier = Modifier) {
    Scaffold(containerColor = primaryDarkHighContrast) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(painter = painterResource(id = R.drawable.splash), contentDescription = null, modifier = Modifier.fillMaxSize())
        }
    }
}