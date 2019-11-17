package com.rahul.clientapp.adapters


import android.content.Context
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahul.clientapp.R
import com.rahul.clientapp.data.database.MedicationWIthMedicines
import com.rahul.clientapp.utils.TimeUtils


class MedicationAdapter(val context: Context, val recyclerView: RecyclerView)   : RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder>() {
    private var allMedications = ArrayList<MedicationWIthMedicines>()
    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationViewHolder {
        return MedicationViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_receipt, parent, false))
    }

    override fun getItemCount() = allMedications.size

    override fun onBindViewHolder(holder: MedicationViewHolder, position: Int) {
        holder.bind(allMedications[position], position)
    }

    inner class MedicationViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val doctorTextView = itemView.findViewById<TextView>(R.id.doctorNameTextView)
        private val diseaseTextView = itemView.findViewById<TextView>(R.id.diseaseNameTextView)
        private val fromTextView = itemView.findViewById<TextView>(R.id.fromDate)
        private val endTextView = itemView.findViewById<TextView>(R.id.endDate)
        private val activeTextView = itemView.findViewById<TextView>(R.id.activeTextView)
        private val medicinesRecyclerView = itemView.findViewById<RecyclerView>(R.id.medicinesRv)

        fun bind(medication: MedicationWIthMedicines, position: Int) {
            doctorTextView.text = medication.medication.doctorName
            diseaseTextView.text = medication.medication.diseaseName

            val isExpanded = position == selectedPosition

            if(medication.medication.endDate > TimeUtils.getTodaysMidninghtTimeInMillis())
                activeTextView.visibility = View.VISIBLE
            else
                activeTextView.visibility = View.INVISIBLE

            medicinesRecyclerView.visibility = if(isExpanded) View.VISIBLE else View.GONE
            itemView.isActivated = isExpanded

            fromTextView.text = TimeUtils.getDateString(medication.medication.startDate)

            val noOfDays = (medication.medication.endDate - medication.medication.startDate)/86400000
            endTextView.text = "$noOfDays days"

            val medicineAdapter = MedicineAdapter(context)
            medicinesRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = medicineAdapter
            }

            medicineAdapter.addAll(medication.medicineList)

            itemView.setOnClickListener {
                selectedPosition = if (isExpanded) -1 else position
                TransitionManager.beginDelayedTransition(recyclerView)
                notifyDataSetChanged()
            }
        }
    }

    fun addNewMedication(medication: MedicationWIthMedicines) {
        allMedications.add(medication)
        notifyItemInserted(allMedications.size - 1)
    }

    fun swapList(medicationList: List<MedicationWIthMedicines>) {
        allMedications.clear()
        allMedications.addAll(medicationList)
        notifyDataSetChanged()
    }
}