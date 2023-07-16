package com.example.testbet.features.web

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.testbet.R

private const val BUNDLE_TAG = "web_view_bundle"
private const val WEB_LINK = "https://leon.ru/bets"
private lateinit var progressBar: ProgressBar
private lateinit var webView: WebView
private val stopList = listOf<String>(
    "appgallery",
    "apps.samsung",
    "https://leon.ru/android",
    "rustore",
    "https://leon.ru/app"
)

class WebFragment : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_fragment)
        webView = findViewById(R.id.MainWebView)
        progressBar = findViewById(R.id.webViewProgressBar)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        CookieManager.getInstance().acceptCookie()
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState.getBundle(BUNDLE_TAG)!!)
        } else {
            with(webView) {
                loadUrl(WEB_LINK)
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                webViewClient = MyWebViewClient()

            }
        }
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (webView.canGoBack()) webView.goBack()
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        val bundle = Bundle()
        webView.saveState(bundle)
        outState.putBundle(BUNDLE_TAG, bundle)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        webView.restoreState(savedInstanceState)
    }

}


private class MyWebViewClient : WebViewClient() {
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        progressBar.visibility = View.GONE
        webView.visibility = View.VISIBLE
    }


    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (checkUrl(url ?: "")) return super.shouldOverrideUrlLoading(view, url)
        else return true
    }

    private fun checkUrl(url: String): Boolean {
        stopList.forEach {
            if (url.contains(it)) return false

        }
        return true
    }
}

private class MyChromeClient() : WebChromeClient() {
    override fun onPermissionRequest(request: PermissionRequest?) {
        request?.grant(request.resources)
    }
}

