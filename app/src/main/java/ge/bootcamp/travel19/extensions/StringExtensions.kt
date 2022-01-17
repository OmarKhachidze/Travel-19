package ge.bootcamp.travel19.extensions

import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Pattern

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches() && !TextUtils.isEmpty(this)
}

private const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
fun String.isValidPassword(): Boolean {
    return this.length > 8 && Pattern.compile(PASSWORD_PATTERN)
            .matcher(this)
            .matches()
}

