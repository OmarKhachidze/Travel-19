package ge.bootcamp.travel19.extensions

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import android.util.Patterns
import ge.bootcamp.travel19.utils.Constants.PASSWORD_PATTERN
import java.util.regex.Pattern

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches() && !TextUtils.isEmpty(this)
}

fun String.isValidPassword(): Boolean {
    return this.length > 8 && Pattern.compile(PASSWORD_PATTERN)
            .matcher(this)
            .matches()
}

fun String.parseHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(
            this,
            Html.FROM_HTML_MODE_COMPACT
        )
    } else {
        Html.fromHtml(
            this,
        )
    }
}

