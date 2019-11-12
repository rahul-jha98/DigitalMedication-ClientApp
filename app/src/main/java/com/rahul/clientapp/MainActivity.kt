package com.rahul.clientapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }

//    private fun initializeServices() {
//        var isEnqueued = false
//        val statuses = WorkManager.getInstance().getWorkInfosForUniqueWork("MedicationService")
//        try {
//            val workInfoList = statuses.get()
//            for (workInfo in workInfoList) {
//                val state = workInfo.state
//                isEnqueued = state == WorkInfo.State.ENQUEUED || state == WorkInfo.State.RUNNING
//            }
//        } catch (e: ExecutionException) {
//            e.printStackTrace()
//            isEnqueued = false
//        } catch (e: InterruptedException) {
//            e.printStackTrace()
//            isEnqueued = false
//        }
//
//        if (!isEnqueued) {
//            createNotificationChannel()
//
//            val data = Data.Builder().putInt("mode", 0).build()
//            val workInitializer = OneTimeWorkRequestBuilder<InitializerWorker>()
//                .setInitialDelay(1, TimeUnit.SECONDS)
//                .setInputData(data)
//                .addTag("initialize")
//                .build()
//
//            WorkManager.getInstance().enqueueUniqueWork("initializer", ExistingWorkPolicy.REPLACE, workInitializer)
//        }
//
//    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Medication"
            val descriptionText = "Get notified of all your active medications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("123", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
