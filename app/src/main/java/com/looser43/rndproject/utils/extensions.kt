package com.looser43.rndproject.utils


import android.app.Activity
import android.app.DatePickerDialog
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.looser43.rndproject.R
import java.text.SimpleDateFormat
import java.util.*


fun Activity.callHelpLine() {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:16479")
    startActivity(intent)
}

fun String.toBanglaDigit(): String {
    val banglaDigits = charArrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')

    if (this == null)
        return ""
    val builder = StringBuilder()
    try {
        for (i in 0 until this.length) {
            if (Character.isDigit(this[i])) {
                if (this[i].toInt() - 48 <= 9) {
                    builder.append(banglaDigits[this.get(i).toInt() - 48])
                } else {
                    builder.append(this.get(i))
                }
            } else {
                builder.append(this[i])
            }
        }
    } catch (e: Exception) {
        //logger.debug("getDigitBanglaFromEnglish: ",e);
        return ""
    }

    return builder.toString()
}

fun Context.getDeviceID(): String {
    return try {
        Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

/*
fun Context.openUrlInBrowser(url: String?) {
    try {
        if (url.isNullOrBlank())
            return

        val intentBuilder = CustomTabsIntent.Builder()
        intentBuilder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
        val customTabsIntent = intentBuilder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url.trim()))
    } catch (e: Exception) {
        logException(e)
    }
}*/

fun Context.loadImage(url: String, holder: ImageView) {
    Glide.with(this)
        .load(url)
        .apply(
            RequestOptions.placeholderOf(R.drawable.banner_placeholder)
                .error(R.drawable.ic_check_circle)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
        )
        .into(holder)

}



fun String.equalIgnoreCase(string: String): Boolean {
    return equals(string, true)
}

fun String.removeLastComma(): String {
    if (endsWith(",")) {
        return substring(0, length - 1)
    }
    return this
}

fun String.countCommas(): Int {
    val someStringArr = this.toCharArray()
    var count = 0
    val someChar = ','

    someStringArr.forEach {
        if (it == someChar)
            count++
    }
    Log.d("CommaCount", "count: $count")
    return count
}

fun Date.toSimpleDateString(): String {
    val format = SimpleDateFormat("dd/MM/yyy")
    return format.format(this)
}

fun Date.toSimpleTimeString(): String {
    val format = SimpleDateFormat("h:mm a")
    return format.format(this)
}

fun View.closeKeyboard(activity: Context) {
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm!!.hideSoftInputFromWindow(this.windowToken, 0)
}

fun pickDate(c: Context, now: Calendar, listener: DatePickerDialog.OnDateSetListener) {
    val dpd = DatePickerDialog(
        c,
        listener,
        // set DatePickerDialog to point to today's date when it loads up
        now.get(Calendar.YEAR),
        now.get(Calendar.MONTH),
        now.get(Calendar.DAY_OF_MONTH)
    )
    dpd.show()
}


fun NestedScrollView.behaveYourself(fab: FloatingActionButton) {
    this.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
        if (scrollY > oldScrollY) {
            fab.hide()
        } else {
            fab.show()
        }
    })
}

fun RecyclerView.behaveYourself(fab: FloatingActionButton) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (dy > 0 || dy < 0 && fab.isShown)
                fab.hide()
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                fab.show()
            }
            super.onScrollStateChanged(recyclerView, newState)
        }
    })
}


fun Any.simpleClassName(fragment: Fragment): String {
    return fragment::class.java.simpleName
}

fun TextInputLayout.showError(errorMessage: String?) {
    isErrorEnabled = true
    error = errorMessage
}

fun TextInputLayout.hideError() {
    isErrorEnabled = false
}


fun TextInputLayout.setError() {
    isErrorEnabled = true
    this.error = "This field can not be empty"
}

fun AppCompatActivity.transitFragment(fragment: androidx.fragment.app.Fragment, holderID: Int) {
    try {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(holderID, fragment, "")
        transaction.commit()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
