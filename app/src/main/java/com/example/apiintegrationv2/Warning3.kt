package com.example.apiintegrationv2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class Warning3 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_warning3, container, false)

        view.findViewById<Button>(R.id.yesBtn)?.setOnClickListener {
            notification()
            val fm = activity?.supportFragmentManager
            val transaction = fm?.beginTransaction()
            transaction?.replace(R.id.frameLayout, Warning1())
            transaction?.addToBackStack("warning1")
            transaction?.commit()
        }

        view.findViewById<Button>(R.id.noBtn)?.setOnClickListener {
            val fm = activity?.supportFragmentManager
            fm?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        return view
    }

    private fun notification() {
        val channelId = "APIV2_ID"
        val notificationId = 1 // A unique ID for the notification

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Wijkarn_Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(channelId, channelName, importance)

            val notificationManager = requireContext().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("API V2")
            .setContentText("Sussy boi")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val intent = Intent(requireContext(), MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        notificationBuilder.setContentIntent(pendingIntent)

        val notificationManager = requireContext().getSystemService(NotificationManager::class.java)
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}