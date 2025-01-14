package vn.id.tool.nlu.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import vn.id.tool.nlu.resource.AppTypography

@Composable
fun HeaderText(modifier: Modifier = Modifier, text: String) {
    Text(
        text = text,
        style = AppTypography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = modifier,
    )
}