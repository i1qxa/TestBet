package com.example.testbet

import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment

class LoadingFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkConfiguration()
    }

    private fun checkConfiguration():Boolean{
        val locale = ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
        val tm = getSystemService(requireContext(), TelephonyManager::class.java) as TelephonyManager
        val countrySim = tm.simCountryIso.lowercase().contains("ru")
        val country = locale?.displayCountry?.lowercase()?.contains("ru")
        val deviceName = Build.DEVICE != ""
        val operatorName = if (!checkVpn()) tm.simOperatorName else true




        return true
    }

    private fun checkVpn():Boolean{
        val cm = getSystemService(requireContext(), ConnectivityManager::class.java) as ConnectivityManager
        val network = cm.activeNetwork
        return cm.getNetworkCapabilities(network)?.hasTransport(NetworkCapabilities.TRANSPORT_VPN)?:false
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoadingFragment()
    }
}