package com.rahul.clientapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.rahul.clientapp.R
import com.rahul.clientapp.adapters.DoctorAdapter
import com.rahul.clientapp.models.Doctor
import kotlinx.android.synthetic.main.activity_search_doctor.*

class SearchDoctorActivity : AppCompatActivity() {
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: DoctorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_doctor)
        var sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        viewManager = LinearLayoutManager(this)
        var clientId: String = sharedPreferences.getString(getString(R.string.client_id),"").toString()
        var clientName: String = sharedPreferences.getString(getString(R.string.client_name),"").toString()
        viewAdapter = DoctorAdapter(clientId, clientName, this)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        FirebaseDatabase.getInstance().getReference("/doctor/").addChildEventListener(object: ChildEventListener{
            override fun onChildRemoved(p0: DataSnapshot) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val doc = p0.getValue(Doctor::class.java)!!
                viewAdapter.addItem(doc)

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onCancelled(p0: DatabaseError) {
            }

        })
    }
}
