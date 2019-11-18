package com.rahul.clientapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rahul.clientapp.R
import com.rahul.clientapp.models.Doctor
import org.w3c.dom.Text


class DoctorAdapter:
    RecyclerView.Adapter<DoctorAdapter.MyViewHolder>() {

    private val docList = ArrayList<Doctor>()

    fun addItem(doc: Doctor){
        docList.add(doc)
        notifyItemInserted(docList.size-1)
    }


    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){
        var docId: TextView = view.findViewById(R.id.textId)
        var docName: TextView = view.findViewById(R.id.textName)
        var docSpecialization: TextView = view.findViewById(R.id.textSpecialization)
        var docLocation: TextView = view.findViewById(R.id.textLocation)

        fun bind(doc: Doctor){
            docId.text = doc.docId
            docName.text = doc.name
            docSpecialization.text = doc.specialization
            docLocation.text = doc.location
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): DoctorAdapter.MyViewHolder {

        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_doctor,parent,false))
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(docList[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = docList.size
}