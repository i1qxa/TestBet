package com.example.testbet

import android.content.Intent
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat
import androidx.core.os.ConfigurationCompat
import com.example.testbet.features.web.WebFragment

private const val LANGUAGE = "ru"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkConfiguration()
    }

    private fun checkConfiguration() {
        val locale = ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
        val tm = ContextCompat.getSystemService(
            this,
            TelephonyManager::class.java
        ) as TelephonyManager
        val checkCountryIso =
            if (!checkVpn()) tm.simCountryIso.lowercase().contains(LANGUAGE) else true
        val checkCountry = (locale?.displayCountry?.lowercase()?.contains("россия")) ?: true
        val checkLanguage = (locale?.language?.lowercase()?.contains(LANGUAGE)) ?: true
        val checkDeviceName = Build.DEVICE.isNotEmpty()
        val operatorName = tm.simOperatorName?.lowercase()?:""
        val checkOperatorName = !operatorName.contains("t-mobile") && operatorName.isNotEmpty()
        val intent =
//        if (checkCountry && checkLanguage && checkCountryIso && checkDeviceName && checkOperatorName){
        if(true){
            Intent(this, WebFragment::class.java)
        }else{
            Intent(this,ContentActivity::class.java)
        }
        startActivity(intent)

    }

    private fun checkVpn(): Boolean {
        val cm = ContextCompat.getSystemService(
            this,
            ConnectivityManager::class.java
        ) as ConnectivityManager
        val network = cm.activeNetwork
        return cm.getNetworkCapabilities(network)?.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
            ?: false
    }

}