package com.rahul.clientapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rahul.clientapp.R
import com.rahul.clientapp.models.Report
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.net.Uri


class ReportAdapter(val context: Context)   : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>(){
    val items = ArrayList<Report>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        return ReportViewHolder((LayoutInflater.from(parent.context).inflate(R.layout.list_item_report, parent, false)))
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(items[position])
    }


    inner class ReportViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val doctorNameView = itemView.findViewById<TextView>(R.id.doctorNameTextView)
        private val diseaseNameView = itemView.findViewById<TextView>(R.id.diseaseNameTextView)

        fun bind(report: Report) {
            doctorNameView.text = report.doctorName
            diseaseNameView.text = report.diseaseName

            itemView.setOnClickListener {
                //open link to url
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(report.iamgeUrl)
                context.startActivity(i)
            }
        }
    }

    fun addItem(report : Report) {
        items.add(report)
        notifyItemInserted(items.size - 1)
    }
}