package vn.id.tool.nlu.core.presentation.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import vn.id.tool.nlu.resource.defaultPadding

@Composable
fun LoadingIndicator() {
    Surface(
        modifier = Modifier
            .height(IntrinsicSize.Min).widthIn(min = 100.dp).heightIn(min = 100.dp),
        shape = RoundedCornerShape(20),
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.background,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.padding(defaultPadding)
        )
    }
}