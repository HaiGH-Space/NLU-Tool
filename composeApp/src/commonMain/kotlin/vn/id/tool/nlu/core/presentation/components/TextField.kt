package vn.id.tool.nlu.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation

import org.jetbrains.compose.resources.stringResource
import vn.id.tool.nlu.res.Res
import vn.id.tool.nlu.res.close_hint

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    labelText: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: @Composable (() -> Unit)? = { TrailingIconDefault(value, onValueChange) },
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    roundedPercent: Int = 20,
    singleLine: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = { if (labelText != null) Text(labelText) },
        leadingIcon = {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        },
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(roundedPercent),
        placeholder = { if (placeholder != null) Text(placeholder) },
        trailingIcon = trailingIcon
    )
}

@Composable
fun TrailingIconDefault(value: String, onValueChange: (String) -> Unit) {
    TrailingIconPlainText(value, onValueChange)
}

@Composable
fun TrailingIconPlainText(value: String, onValueChange: (String) -> Unit) {
    AnimatedVisibility(
        visible = value.isNotBlank()
    ) {
        IconButton(
            onClick = {
                onValueChange("")
            }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(Res.string.close_hint),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun TrailingIconPassword(passwordVisible: Boolean,value: String ,onClickShowPassword: () -> Unit) {
    AnimatedVisibility(
        visible = value.isNotBlank()
    ) {
        val image = if (passwordVisible)
            Icons.Default.Visibility
        else  Icons.Default.VisibilityOff
        IconButton(
            onClick =
            onClickShowPassword
        ) {
            Icon(
                imageVector = image,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}