package ge.bootcamp.travel19.extensions

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import ge.bootcamp.travel19.R

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
    this.setOnClickListener {
        if (this.isChecked) {
            thumbTintList = ContextCompat.getColorStateList(this.context, R.color.secondary_main)
            trackTintList = ContextCompat.getColorStateList(this.context, R.color.secondary_main50)
        }else{
            thumbTintList = ContextCompat.getColorStateList(this.context, R.color.light_black)
            trackTintList = ContextCompat.getColorStateList(this.context, R.color.light_black50)
        }
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