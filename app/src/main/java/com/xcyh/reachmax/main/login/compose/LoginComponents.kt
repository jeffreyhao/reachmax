package com.xcyh.reachmax.main.login.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xcyh.reachmax.R

private val FieldBg = Color(0xFFF6F6F6)
private val FieldHint = Color(0xFFBFBFBF)
private val FieldText = Color(0xFF666666)

/**
 * 登录输入框，视觉对应 bg_login_edit.xml（圆角22dp 灰底）。
 *
 * @param passwordVisible 密码是否明文显示（仅当 [onTogglePassword] 非空时生效）
 * @param onTogglePassword 非空时表示这是密码框，显示眼睛图标用于切换显隐
 */
@Composable
fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    passwordVisible: Boolean = false,
    onTogglePassword: (() -> Unit)? = null,
) {
    val isPasswordField = onTogglePassword != null
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 45.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(FieldBg)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(color = FieldText, fontSize = 13.sp),
            cursorBrush = SolidColor(FieldText),
            keyboardOptions = if (isPasswordField) {
                KeyboardOptions(
                    keyboardType = if (passwordVisible) KeyboardType.Text else KeyboardType.Password
                )
            } else {
                KeyboardOptions.Default
            },
            visualTransformation = if (isPasswordField && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(hint, color = FieldHint, fontSize = 13.sp)
                }
                innerTextField()
            },
        )
        if (isPasswordField) {
            IconButton(
                onClick = onTogglePassword!!,
                modifier = Modifier.align(Alignment.CenterEnd),
            ) {
                Icon(
                    painter = painterResource(
                        if (passwordVisible) R.drawable.ic_eye_open else R.drawable.ic_eye_close
                    ),
                    contentDescription = if (passwordVisible) "隐藏密码" else "显示密码",
                    tint = Color.Unspecified,
                )
            }
        }
    }
}

/**
 * 登录按钮，视觉对应 bg_login_button.xml（圆角22dp，默认 colorAccent，按下 colorAccentPress）。
 */
@Composable
fun LoginButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    // colorAccent = #559AF0, colorAccentPress = #458AE0
    val accent = Color(0xFF559AF0)
    val accentPress = Color(0xFF458AE0)
    val bg = when {
        !enabled -> accent.copy(alpha = 0.5f)
        pressed -> accentPress
        else -> accent
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 45.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(bg)
            .then(
                if (enabled) {
                    Modifier.clickable(
                        interactionSource = interaction,
                        indication = null,
                        onClick = onClick,
                    )
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(text, color = Color.White, fontSize = 13.sp)
    }
}
