package com.rahul.clientapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.rahul.clientapp.backgroundworkers.ReminderService
import com.rahul.clientapp.models.Medication
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val repository = (application as ClinetApplication).applicationComponent.getRepository()

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        FirebaseDatabase.getInstance().getReference("data").setValue("Hello")
        FirebaseDatabase.getInstance().getReference("/medications/dhruv/").addChildEventListener(object :ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                Log.d("Firebase", p0.key)
                val medication = p0.getValue(Medication::class.java)!!
                val id = p0.key.toString()
                Log.d("Firebase", "Added entry "+medication.doctorName)
                repository.addMedication(medication, id)

            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

        })

        initializeServices()
    }

    private fun initializeServices() {
        createNotificationChannel()
        val MILLIS_IN_DAY: Long = 86400000
        val currentInstance = Calendar.getInstance()
        val currentTime = currentInstance.timeInMillis

        currentInstance.set(Calendar.HOUR_OF_DAY, 0)
        currentInstance.set(Calendar.MINUTE, 0)
        currentInstance.set(Calendar.SECOND, 0)
        currentInstance.set(Calendar.MILLISECOND, 0)

        val millisecondsForMidnight = currentInstance.timeInMillis

        val MILLIS_IN_HOUR = MILLIS_IN_DAY / 24

        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val nextTimeSlot = when(hour){
            0-8-> millisecondsForMidnight + 9 * MILLIS_IN_HOUR
            9-13-> millisecondsForMidnight + 14 * MILLIS_IN_HOUR
            14-21 -> millisecondsForMidnight + 22 * MILLIS_IN_HOUR
            else -> millisecondsForMidnight + (24 + 9) * MILLIS_IN_HOUR
        }

        val notificationWork = OneTimeWorkRequest.Builder(ReminderService::class.java)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .addTag("notificationWork")
            .build()

        WorkManager.getInstance().beginUniqueWork("reminderservice", ExistingWorkPolicy.KEEP,notificationWork).enqueue()
    }

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
