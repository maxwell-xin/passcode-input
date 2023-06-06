package com.maxwellxin.passcode_input

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
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

            editText.setOnKeyListener(object : OnKeyListener {
                override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
                    if (p1 == KeyEvent.KEYCODE_DEL) {
                        textRemove()
                        return true
                    }
                    return false
                }
            })

            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    textInsert(p0.toString().trim())
                }
            })
        }

        attrs?.let {
            applyCustomProperty(it)
        }
    }


    private fun applyCustomProperty(attrs: AttributeSet) {
        context.theme.obtainStyledAttributes(attrs, androidx.constraintlayout.widget.R.styleable.ConstraintLayout_Layout, 0, 0)
    }

    fun focus(position: Int) {
        editTextList[position].requestFocus()
    }

    fun reset() {
        for (editText in editTextList) {
            editText.setText("")
        }
        editTextList[0].isEnabled = true
    }

    private fun textInsert(text: String) {
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
        }
    }

    private fun textRemove() {
        val index = editTextList.indexOf(currentFocus)
        val editText: EditText = editTextList[index]

        if (editText.text.isNotEmpty()) {
            editText.text.clear()
        } else if (index > 0) {
            editText.isEnabled = false
            editTextList[index - 1].isEnabled = true
            editTextList[index - 1].requestFocus()
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