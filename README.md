## Passcode Input Field (6 digits)

## Installation

Add it in your root build.gradle at the end of repositories:

```bash
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Add the dependency:

```bash
dependencies {
  implementation 'com.github.maxwell-xin:passcode-input:Tag'
}
```

Simple Usage
------------

Example:

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
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
```
