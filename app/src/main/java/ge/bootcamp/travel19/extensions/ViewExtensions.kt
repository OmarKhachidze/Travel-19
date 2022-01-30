package ge.bootcamp.travel19.extensions

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.utils.Constants.ERROR_TAG

fun View.showSnack(message: String, color: Int) {
    val snackBar: Snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    val snackBarView = snackBar.view
    snackBarView.backgroundTintList = ContextCompat.getColorStateList(this.context, color)
    snackBar.show()
}

fun View.visible(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

fun View.gone(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}

fun View.invisible(): View {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
    return this
}

fun SwitchMaterial.setUpSwitch() {
    this.isChecked = false
    this.setOnClickListener {
        if (this.isChecked) {
            thumbTintList = ContextCompat.getColorStateList(this.context, R.color.secondary_main)
            trackTintList = ContextCompat.getColorStateList(this.context, R.color.secondary_main50)
        } else {
            thumbTintList = ContextCompat.getColorStateList(this.context, R.color.light_black)
            trackTintList = ContextCompat.getColorStateList(this.context, R.color.light_black50)
        }
    }
}


fun ShimmerFrameLayout.hide(color: Int) {
    this.stopShimmer()
    this.hideShimmer()
    this.children.forEach {
        if (it is LinearLayoutCompat) {
            it.children.forEach { children ->
                if (children is AppCompatTextView) {
                    children.background = null
                    children.setTextColor(ContextCompat.getColor(children.context, color))
                } else if (children is LinearLayoutCompat) {
                    children.children.forEach { deepChildren ->
                        if (deepChildren is AppCompatTextView) {
                            deepChildren.background = null
                            deepChildren.setTextColor(
                                ContextCompat.getColor(
                                    children.context,
                                    color
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

fun ShimmerFrameLayout.loading(shouldLoad: Boolean) {
    if (shouldLoad)
        this.startShimmer()
    else
        this.hide(R.color.white)
}

fun ViewGroup.hideAllView(confirmation: Boolean) {
    this.children.forEach {
        if (it.tag == ERROR_TAG) {
            if (confirmation)
                it.visible()
            else
                it.gone()
        } else
            if (confirmation)
                it.gone()
            else it.visible()
    }
}

fun ImageView.setDrawable(icon: Int) {
    this.setImageDrawable(
        ContextCompat.getDrawable(
            this.context,
            icon
        )
    )
}

fun CircularProgressIndicator.setUpProgress(progress: Double?, textView: AppCompatTextView) {
    this.setProgressCompat(
        progress?.toInt() ?: 0,
        true
    )
    textView.text =
        (progress?.toInt() ?: 0).toString().plus(resources.getString(R.string.percentage))
}

fun ImageView.setNetworkImage(
    cover: String?,
    placeHolder: Int = R.drawable.ic_outlined_flag,
    errorImage: Int = R.drawable.ic_filled_flag
) {
    if (cover != null) {
        Glide.with(context)
            .load(cover)
            .error(errorImage)
            .placeholder(placeHolder)
            .into(this)
    } else {
        this.setImageResource(R.drawable.ic_outlined_flag)
    }

}

fun Chip.setUp(bool: Boolean) {
    if (bool) {
        this.setChipIconResource(R.drawable.ic_check)
        this.setChipIconTintResource(R.color.chipTintGreen)
        this.setChipBackgroundColorResource(R.color.chipBackgroundGreen)
    } else {
        this.setChipIconResource(R.drawable.ic_cancel)
        this.setChipBackgroundColorResource(R.color.chipBackgroundRed)
    }
}

fun TextInputLayout.validateInput(hasError: Int?) {
    val shake: Animation = AnimationUtils.loadAnimation(this.context, R.anim.vibrate)
    if (hasError != null) {
        startAnimation(shake)
        error = resources.getString(hasError)
        this.showSnack(
            resources.getString(hasError),
            R.color.error_red
        )
    } else
        error = null
}

fun EditText.validateInput(hasError: Int?) {
    val shake: Animation = AnimationUtils.loadAnimation(this.context, R.anim.vibrate)
    if (hasError != null) {
        startAnimation(shake)
        error = resources.getString(hasError)
        this.showSnack(
            resources.getString(hasError),
            R.color.error_red
        )
    } else
        error = null
}

fun AppCompatButton.setData(text: Int, pr: CircularProgressIndicator, isVisible: Boolean) {
    pr.isVisible = isVisible
    this.text = if (isVisible) null else resources.getString(text)
    isEnabled = !isVisible
    isClickable = !isVisible
}

fun AutoCompleteTextView.setData(
    text: String? = null,
    errorText: String? = null,
    adapterList: List<String>? = null
) {
    error = errorText
    setText(text)
    setAdapter(
        ArrayAdapter(
            context,
            R.layout.list_item,
            adapterList ?: listOf()
        )
    )
}