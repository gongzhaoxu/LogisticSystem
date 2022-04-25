package com.example.logisticsystem

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.logisticsystem.databinding.ActivityWebviewBinding

class WebViewActivity: AppCompatActivity() {

    private lateinit var binding: ActivityWebviewBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.webview.settings.setJavaScriptEnabled(true)//支持javascript脚本
        binding.webview.webViewClient = WebViewClient()//
        //当页面跳转时，目标页面仍在当前webView中显示，不打开浏览器
        binding.webview.loadUrl("https://www.gzx.asia/%e7%95%99%e8%a8%80/")

    }
}