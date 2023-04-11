package com.maxwellxin.passcode_input

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import com.maxwellxin.passcode_input.databinding.SamplePasscodeInputBinding

class PasscodeInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs) {

    private var binding: SamplePasscodeInputBinding = SamplePasscodeInputBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    private var editTextList: List<EditText> = listOf(
        binding.digits1,
        binding.digits2,
        binding.digits3,
        binding.digits4,
        binding.digits5,
        binding.digits6,
    )

    private var currentFocus: EditText? = null

    //Listener
    private var listener: PasscodeListener? = null

    init {
        for (editText in editTextList) {
            editText.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    currentFocus = v as EditText
                }
            }

            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    textInserted(p0.toString())
                }
            })
        }

        attrs?.let {
            applyCustomProperty(it)
        }
    }

    fun reset() {
        for (editText in editTextList) {
            editText.setText("")
        }
    }

    private fun applyCustomProperty(attrs: AttributeSet) {
        context.theme.obtainStyledAttributes(attrs, androidx.constraintlayout.widget.R.styleable.ConstraintLayout_Layout, 0, 0)
    }

    private fun textInserted(text: String) {
        val index = editTextList.indexOf(currentFocus)
        if (text.isNotEmpty()) {
            //Completed and Submit
            if (index == 5) {
                if (listener != null) {
                    var pin = ""
                    for (editText in editTextList) {
                        pin += editText.text.trim()
                    }
                    listener!!.completed(pin)
                }
                return
            }
            editTextList[index + 1].isEnabled = true
            editTextList[index + 1].requestFocus()
            editTextList[index].isEnabled = false

        } else {
            if (index == 0) {
                return
            }
            editTextList[index - 1].isEnabled = true
            editTextList[index - 1].requestFocus()
            editTextList[index].isEnabled = false
        }
    }

    /**
     * Interface
     */
    interface PasscodeListener {
        fun completed(value: String)
    }

    fun setPasscodeListener(passcodeListener: PasscodeListener) {
        listener = passcodeListener
    }
}