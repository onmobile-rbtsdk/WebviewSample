package com.onmo.webviewsample.activity

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.onmo.webviewsample.R
import com.samples.drtumifancentral.utils.Utils

/**
 * Created by Srini on 11/9/2022.
 *
 * @author Srini
 */
class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var dialog: AlertDialog
    private var doubleBackToExitPressedOnce = false
    private var defaultLoadingUrl = "https://play.onmo.com/"
    private val redirectionHomeScreenUrl = "https://play.onmo.com/"
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (intent != null && intent.extras!!.getString("URL") != null) {
            defaultLoadingUrl = intent.extras!!.getString("URL").toString()
            Log.d("URL", "URL->$defaultLoadingUrl")
        }
        initViews()
        loadWebView(webView)
    }

    private fun initViews() {
        webView = findViewById(R.id.webView)
        webView.setBackgroundColor(Color.TRANSPARENT)
        progressBar = findViewById(R.id.progress)
        initDialog("")
    }

    private fun initDialog(message: String) {
        dialog = AlertDialog.Builder(this@MainActivity).setMessage(message)
            .setPositiveButton("Retry") { _, _ ->
                dialog.dismiss()
                loadWebView(webView)
            }
            .setCancelable(false)
            .create()
    }

    private fun loadWebView(webView: WebView) {
        if (Utils.isNetworkAvailable(this@MainActivity)) {
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    Log.d(TAG, "shouldOverrideUrlLoading: "+url)
                    view?.loadUrl(url!!)
                    return true
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    Log.d(TAG, "onPageStarted: ")
                    super.onPageStarted(view, url, favicon)
                    progressBar.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    Log.d(TAG, "onPageFinished: ")
                    super.onPageFinished(view, url)
                    progressBar.visibility = View.GONE
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    Log.d(TAG, "onReceivedError: ${error?.description}")
                    Log.d(TAG, "onReceivedError: ${error?.errorCode}")
                    super.onReceivedError(view, request, error)
                    view?.loadUrl("about:blank")
                    view?.invalidate()
                    showAlert("Oops, ran into a problem. Please try again")
                }

                override fun onReceivedHttpError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    errorResponse: WebResourceResponse?
                ) {
                    Log.d(TAG, "onReceivedHttpError: ${errorResponse?.statusCode}")
                    super.onReceivedHttpError(view, request, errorResponse)
                }


            }
            webView.settings.javaScriptEnabled = true
            webView.settings.domStorageEnabled = true
            webView.settings.setSupportZoom(false)
            webView.loadUrl(defaultLoadingUrl)
        } else {
            showAlert("No internet connection. Please try again.")
        }
    }

    private fun showAlert(message: String) {

        if (dialog.isShowing) {
            return
        }
        initDialog(message)
        dialog.show()
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d(TAG, "onKeyDown: webview url = ${webView.url}")
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            if (webView.url.equals(defaultLoadingUrl) || webView.url.equals(redirectionHomeScreenUrl)) {
                handleBackPress()
            } else {
                webView.goBack()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun handleBackPress() {
        if (doubleBackToExitPressedOnce) {
            finish()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}