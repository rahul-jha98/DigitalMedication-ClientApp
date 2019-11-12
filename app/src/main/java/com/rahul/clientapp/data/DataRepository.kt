package com.rahul.clientapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.rahul.clientapp.AppExecutors
import com.rahul.clientapp.data.database.MedicationDao
import com.rahul.clientapp.data.database.MedicationEntry
import com.rahul.clientapp.data.database.MedicationWIthMedicines
import com.rahul.clientapp.data.database.MedicineEntry
import com.rahul.clientapp.models.Medication
import com.rahul.clientapp.models.Medicine

class DataRepository(private var mMedicationDao: MedicationDao,
                     private var mExecutors: AppExecutors) {
    companion object {
        private val LOG_TAG = DataRepository::class.java.simpleName

        private val LOCK = Object()
        private var sInstance: DataRepository? = null
        private var mInitialized = false

        @Synchronized
        fun getInstance(
            mMedicationDao: MedicationDao,
            mExecutors: AppExecutors
        ): DataRepository {
            Log.d(LOG_TAG, "Getting the repository")
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = DataRepository(mMedicationDao, mExecutors)
                    Log.d(LOG_TAG, "Made a new repository")
                }
            }
            return sInstance!!
        }
    }

    fun init() {
        addMedication(Medication("Dr. Anant Rai", "Cancer", 300, 400, listOf(
            Medicine("Crocin", "1 pill", 1, 0, 1),
            Medicine("Vicks", "1", 0, 1, 1)
        )), 1)

        addMedication(Medication("Dr. Dhruv Agarwal", "Cancer", 300, 400, listOf(
            Medicine("Crocin", "1 pill", 1, 0, 1),
            Medicine("Vicks", "1", 0, 1, 1)
        )), 2)

        addMedication(Medication("Dr. Rahul Jha", "Cancer", 300, 400, listOf(
            Medicine("Crocin", "1 pill", 1, 0, 1),
            Medicine("Vicks", "1", 0, 1, 1),
            Medicine("Apple", "1", 1, 0, 0)
        )), 3)
    }

    fun getAllMedications() : LiveData<List<MedicationWIthMedicines>> {
        return mMedicationDao.getAllMedications()
    }

    @Synchronized
    fun addMedication(medication : Medication, id: Int) {
        mExecutors.diskIO().execute{
            var mediationEntry = MedicationEntry(id, medication.doctorName, medication.diseaseName,
                                                    medication.startDate, medication.endDate)

            val list = ArrayList<MedicineEntry>()

            for(medicine in medication.medicines)
                list.add(MedicineEntry(medicine.name, medicine.quantity, medicine.breakfast, medicine.lunch, medicine.dinner, id))

            mMedicationDao.insertMedication(mediationEntry)
            mMedicationDao.insertMedicines(list)
        }
    }
}