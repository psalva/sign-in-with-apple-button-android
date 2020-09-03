package com.willowtreeapps.signinwithapplebutton.view

import android.graphics.Bitmap
import android.os.Handler
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.willowtreeapps.signinwithapplebutton.SignInWithAppleService
import java.lang.Exception

internal class SignInWebViewClient(
    private val attempt: SignInWithAppleService.AuthenticationAttempt,
    private val javascriptToInject: String,
    private val  progressCallback :ProgressCallback
) : WebViewClient() {
    var mainHandler= Handler()
    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {

        if(request?.method == "POST" && request?.url.toString().contains(attempt.redirectUri)){
            try {
                Thread.currentThread().interrupt()
            }catch (ex: Exception){}

            mainHandler.post {
                view?.stopLoading()
                view?.loadUrl("javascript:$javascriptToInject")
            }
        }
        return super.shouldInterceptRequest(view, request)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        progressCallback.isLoading(false)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        progressCallback.isLoading(true)
    }
}

interface ProgressCallback{
    fun isLoading(loading:Boolean)
}
