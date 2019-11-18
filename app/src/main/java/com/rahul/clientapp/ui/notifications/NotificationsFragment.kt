package com.rahul.clientapp.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.rahul.clientapp.R
import com.rahul.clientapp.adapters.ReportAdapter
import com.rahul.clientapp.models.Report
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment() {

    private lateinit var reportAdapter: ReportAdapter
    private var childEventListener = object : ChildEventListener{
        override fun onCancelled(p0: DatabaseError) {

        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {

        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {

        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            Log.d("Firebase", p0.value.toString())
            val data = p0.getValue(Report::class.java)!!
            Log.d("Firebase", data.doctorName)
            reportAdapter.addItem(data)
            progressBar.visibility = View.GONE
        }

        override fun onChildRemoved(p0: DataSnapshot) {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reportAdapter = ReportAdapter(activity!!)

        reportRecycerView.apply {
                layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                adapter = reportAdapter
        }

        FirebaseDatabase.getInstance().getReference("/reports/dhruv").addChildEventListener(childEventListener)
    }
}