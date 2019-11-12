package com.rahul.clientapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rahul.clientapp.R
import com.rahul.clientapp.data.database.MedicineEntry
import com.rahul.clientapp.models.Medicine

class MedicineAdapter(val context: Context) : RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    private val colorWhitishGray = ContextCompat.getColor(context, R.color.whitishGray)
    private val colorDeepGray = ContextCompat.getColor(context, R.color.deepGray)

    private var allMedicines = ArrayList<MedicineEntry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        return MedicineViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_medicine, parent, false))
    }

    override fun getItemCount(): Int = allMedicines.size

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        holder.bind(allMedicines[position])
    }

    inner class MedicineViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView = itemView.findViewById<TextView>(R.id.medicineNameTextView)
        private val quantityTextView = itemView.findViewById<TextView>(R.id.quantityTextView)
        private val morningIcon = itemView.findViewById<ImageView>(R.id.morningIcon)
        private val afternoonIcon = itemView.findViewById<ImageView>(R.id.afternoonIcon)
        private val nightIcon = itemView.findViewById<ImageView>(R.id.nightIcon)
        fun bind(medicine: MedicineEntry) {
            nameTextView.text = medicine.name
            quantityTextView.text = medicine.quantity

            morningIcon.setColorFilter(when(medicine.breakfast) {
                0 -> colorWhitishGray
                else -> colorDeepGray
            })

            afternoonIcon.setColorFilter(when(medicine.lunch) {
                0 -> colorWhitishGray
                else -> colorDeepGray
            })

            nightIcon.setColorFilter(when(medicine.dinner) {
                0 -> colorWhitishGray
                else -> colorDeepGray
            })
        }
    }

    fun addAll(newMedicines : List<MedicineEntry>) {
        allMedicines.clear()
        for(newMedicine in newMedicines) {
            allMedicines.add(newMedicine)
            Log.e("Added", newMedicine.name)
        }

        notifyDataSetChanged()
    }
}