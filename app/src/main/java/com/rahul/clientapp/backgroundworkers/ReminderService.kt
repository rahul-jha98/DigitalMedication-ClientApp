package com.rahul.clientapp.backgroundworkers

import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.rahul.clientapp.ClinetApplication
import java.util.*
import java.util.concurrent.TimeUnit

class ReminderService(val context: Context, val params : WorkerParameters) : Worker(context, params) {


    override fun doWork(): Result {

        // currentTime will hold the current time in milliseconds
        //millisecondsUntilMidnight will hold milliseconds for midnight
        val MILLIS_IN_DAY: Long = 86400000
        val currentTime = System.currentTimeMillis()
        val millisTillNow = currentTime % MILLIS_IN_DAY
        val millisecondsForMidnight = currentTime - millisTillNow

        val MILLIS_IN_HOUR = MILLIS_IN_DAY / 24

        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        val timeString = when(hour){
            in 0..9-> "Morning"
            in 9..14-> "Afternoon"
            else -> "Night"
        }

        val nextTimeSlot = when(hour){
            in 0..8-> millisecondsForMidnight + 9 * MILLIS_IN_HOUR
            in 9..13-> millisecondsForMidnight + 14 * MILLIS_IN_HOUR
            in 14..21 -> millisecondsForMidnight + 22 * MILLIS_IN_HOUR
            else -> millisecondsForMidnight + (24 + 9) * MILLIS_IN_HOUR
        }

        val repository = (context as ClinetApplication).applicationComponent.getRepository()
        val allMedications = repository.getMedication(hour, currentTime)

        Log.e("Service", "Got data from repository for $hour. Size of data is ${allMedications.size}")

        if(allMedications.isNotEmpty()) {
            var medicationDisplay = ""
            for(medication in allMedications){
                medicationDisplay+="\n${medication.name} - ${medication.quantity}"
            }

            var mBuilder = NotificationCompat.Builder(context, "123")
                .setSmallIcon(android.R.drawable.alert_light_frame)
                .setContentTitle("Time for your $timeString medicines")
                .setContentText("You have ${allMedications.size} medicines in your prescription")
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("You have ${allMedications.size} medicines in your prescription $medicationDisplay"))
                .setPriority(NotificationCompat.PRIORITY_LOW)

            with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define
                notify((hour * 1234), mBuilder.build())
            }
        }

        val notificationWork = OneTimeWorkRequest.Builder(ReminderService::class.java)
            .setInitialDelay(nextTimeSlot - currentTime, TimeUnit.MILLISECONDS)
            .addTag("notificationWork")
            .build()

        WorkManager.getInstance().beginUniqueWork("reminderservice", ExistingWorkPolicy.REPLACE,notificationWork).enqueue()

        return Result.success()
    }

}