package vn.id.tool.nlu

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import vn.id.tool.nlu.api.nlu.Constant
import vn.id.tool.nlu.api.nlu.Constant.Companion.createConstant


import vn.id.tool.nlu.navigation.Navigation
import vn.id.tool.nlu.resource.AppTheme

@Composable
@Preview
fun App() {
    AppTheme {
        val scope = rememberCoroutineScope()
        Surface(modifier = Modifier.fillMaxSize()) {
            scope.launch {
                createConstant()
            }
            val navController = rememberNavController()
            Navigation(navController)
        }
    }
}