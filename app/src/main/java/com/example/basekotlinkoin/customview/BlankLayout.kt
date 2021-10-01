package com.example.basekotlinkoin.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.basekotlinkoin.R
import kotlinx.android.synthetic.main.layout_blank.view.*

class BlankLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(
    context,
    attrs,
    defStyleAttr
) {

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_blank, this, true)
        attrs?.let {
            val styledAttributes = context.obtainStyledAttributes(it, R.styleable.LayoutBlank, 0, 0)
            val img = styledAttributes.getResourceId(
                R.styleable.LayoutBlank_img,
                0
            )
            val text = styledAttributes.getString(
                R.styleable.LayoutBlank_text
            )
            val type = styledAttributes.getInt(
                R.styleable.LayoutBlank_blankType,
                0
            )
            setType(type)
            setImg(img)
            setText(text)
            styledAttributes.recycle()
        }
    }

    fun setType(value: Int) {
        when (value) {
            0 -> img_blank.setImageResource(R.drawable.ic_grfx_grfx_blank_default)
            1 -> img_blank.setImageResource(R.drawable.ic_grfx_grfx_blank_history)
            2 -> img_blank.setImageResource(R.drawable.ic_grfx_blank_search)
            3 -> img_blank.setImageResource(R.drawable.ic_grfx_warning)
            4 -> img_blank.setImageResource(R.drawable.ic_grfx_grfx_sad)
            5 -> img_blank.setImageResource(R.drawable.ic_grfx_grfx_status_failed)
        }
    }

    fun setImg(value: Int) {
        if (value != 0) {
            img_blank.setImageResource(value)
        }
    }

    fun setText(value: String?) {
        if (value != null) {
            txt_blank.text = value
        }
    }
}