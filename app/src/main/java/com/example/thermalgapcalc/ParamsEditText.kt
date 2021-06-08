package com.example.thermalgapcalc

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

const val DEFAULT = "0"
const val POINT = "."

class ParamsEditText : AppCompatEditText {

    constructor(context: Context) : super(context) {
        this.setWillNotDraw(false)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.setWillNotDraw(false)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        this.setWillNotDraw(false)
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {

    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (text!!.isEmpty() || text.toString() == POINT) {
            setText(DEFAULT)
        }
    }
}