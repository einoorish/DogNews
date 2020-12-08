package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_web_view.*
import android.view.MotionEvent



class WebView : AppCompatActivity() {

    var url:String? = "https://www.google.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        url = intent.getStringExtra("url")

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }
        }

        // chromium, enable hardware acceleration
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)

        webView.setOnTouchListener { _, event -> event.action == MotionEvent.ACTION_MOVE }

        url?.let { webView.loadUrl(it) }
    }
}
