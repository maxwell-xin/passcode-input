package com.maxwellxin.passcode_input

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        val passcodeInput: PasscodeInput = PasscodeInput(this)

        setContent {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    AndroidView(
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth(),
                        factory = { context ->
                            passcodeInput.apply {
                                setPasscodeListener(object : PasscodeInput.PasscodeListener {
                                    override fun completed(value: String) {
                                        Toast.makeText(context, "completed: $value", Toast.LENGTH_SHORT).show()
                                    }
                                })
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            passcodeInput.reset()
                            passcodeInput.focus(0)
                        },
                    ) {
                        Text(
                            text = "Clear Text",
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }
    }
}