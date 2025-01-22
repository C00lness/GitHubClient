package com.example.gitclient

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gitclient.ui.theme.GitClientTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitClientTheme {
                Text(
                    text = "Hello!",
                    modifier = Modifier.padding(57.dp)
                )
                //viewModel.getUsers("ABCD+in:login")
                //viewModel.getRepositories("ABCD+in:name")
                viewModel.getCombinedData()
            }
        }
    }
}


