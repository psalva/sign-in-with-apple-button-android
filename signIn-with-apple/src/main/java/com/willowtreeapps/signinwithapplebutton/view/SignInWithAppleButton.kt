package com.willowtreeapps.signinwithapplebutton.view

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.google.android.material.button.MaterialButton
import com.willowtreeapps.signinwithapplebutton.*

class SignInWithAppleButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialButton (context, attrs, defStyleAttr)  {

    internal companion object {
        const val SIGN_IN_WITH_APPLE_LOG_TAG = "SIGN_IN_WITH_APPLE"
    }

    fun setUpSignInWithAppleOnClick(
        fragmentManager: FragmentManager,
        configuration: SignInWithAppleConfiguration,
        callback: (SignInWithAppleResult) -> Unit
    ) {
        val fragmentTag = "SignInWithAppleButton-$id-SignInWebViewDialogFragment"
        val service = SignInWithAppleService(fragmentManager, fragmentTag, configuration, callback)
        setOnClickListener { service.show() }
    }

    fun setUpSignInWithAppleOnClick(
        fragmentManager: FragmentManager,
        configuration: SignInWithAppleConfiguration,
        callback: SignInWithAppleCallback
    ) {
        setUpSignInWithAppleOnClick(fragmentManager, configuration, callback.toFunction())
    }
}
