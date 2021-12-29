package ge.bootcamp.travel19.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import ge.bootcamp.travel19.R

fun ImageView.setNetworkImage(
    cover: String?,
    placeHolder: Int = R.mipmap.ic_launcher,
    errorImage: Int = R.mipmap.ic_launcher
) {

    if (cover != null) {
        Glide.with(context)
            .load(cover)
            .error(errorImage)
            .placeholder(placeHolder)
            .into(this)
    } else {
        this.setImageResource(R.mipmap.ic_launcher)
    }

}