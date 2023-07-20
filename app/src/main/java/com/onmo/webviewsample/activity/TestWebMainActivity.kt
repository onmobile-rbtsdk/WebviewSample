package com.onmo.webviewsample.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.SwitchCompat
import com.onmo.webviewsample.R
/**
 * Created by Srini on 11/9/2022.
 *
 * @author Srini
 */
class TestWebMainActivity : AppCompatActivity() {
    private val defaultLoadingUrl = "https://challengesarena.com/demo/home"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.url_field_activity_main)
        initViews()
    }

    lateinit var urlField: AppCompatEditText
    lateinit var go: AppCompatButton
    private lateinit var customTabSwitch: SwitchCompat

    private var isCustomTabSwitch : Boolean = false
    private fun initViews() {
        urlField = findViewById(R.id.urlfield)
        go = findViewById(R.id.go)
        customTabSwitch = findViewById(R.id.custom_tab_switch)
        customTabSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            isCustomTabSwitch = isChecked
        }

        go.setOnClickListener()
            { lauchWeb() }
        urlField.setText(defaultLoadingUrl)
    }

    protected fun lauchWeb() {

        if(isCustomTabSwitch)
        intent = Intent(this, CustomTabActivity::class.java)
        else intent = Intent(this, WebviewActivity::class.java)
        val url = urlField!!.text.toString()
        intent.putExtra("URL", url)
       /* Log.d("Test","url ->"+url)
        Log.d("Test","isCustomTabSwitch ->"+isCustomTabSwitch)*/
        startActivity(intent)
    }
}