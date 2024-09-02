package com.nextlevelprogrammers.torchvista

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private lateinit var appTorchManager: AppTorchManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appTorchManager = AppTorchManager(this)

        setContent {
            TorchApp(appTorchManager)
        }
    }
}

@Composable
fun TorchApp(appTorchManager: AppTorchManager) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Button(onClick = { appTorchManager.toggleTorch() }) {
            Text(text = "Toggle Torch")
        }
    }
}