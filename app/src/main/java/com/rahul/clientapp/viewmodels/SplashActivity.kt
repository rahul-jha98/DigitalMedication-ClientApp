package com.rahul.clientapp.viewmodels

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.rahul.clientapp.MainActivity
import com.rahul.clientapp.R
import com.rahul.clientapp.registration.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        val isVerfied = sharedPref.getBoolean(getString(R.string.pref_Verified), false)
        Log.d("Splash", isVerfied.toString())
        if(isVerfied) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


    }
}