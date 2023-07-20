package com.onmo.webviewsample.activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.onmo.webviewsample.R
/**
 * Created by Srini on 11/9/2022.
 *
 * @author Srini
 */
class CustomTabActivity : AppCompatActivity() {
    private var defaultLoadingUrl:String = "https://play.onmo.com/"
    private var package_name = "com.android.chrome";
    private val REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_chrome_tab)
        if (intent != null && intent.extras!!.getString("URL") != null) {
            defaultLoadingUrl = intent.extras!!.getString("URL").toString()
            Log.d("URL", "URL->$defaultLoadingUrl")
        }

        openUrl()
    }


    private fun openUrl() {
        val customIntent = CustomTabsIntent.Builder()
        val params = CustomTabColorSchemeParams.Builder()
        params.setToolbarColor(ContextCompat.getColor(this@CustomTabActivity, com.google.android.material.R.color.design_default_color_primary))
//        params.setToolbarColor(ContextCompat.getColor(this@CustomChromeTab, R.color.black))
        customIntent.setDefaultColorSchemeParams(params.build())
        customIntent.setShowTitle(true)
        customIntent.setShareState(CustomTabsIntent.SHARE_STATE_ON)
        customIntent.setInstantAppsEnabled(true)

        //  To use animations use -
        //  builder.setStartAnimations(this, android.R.anim.start_in_anim, android.R.anim.start_out_anim)
        //  builder.setExitAnimations(this, android.R.anim.exit_in_anim, android.R.anim.exit_out_anim)
        val customBuilder = customIntent.build()
        customBuilder.intent.setPackage(package_name)
        customBuilder.launchUrl(this, Uri.parse(defaultLoadingUrl))
    }
}