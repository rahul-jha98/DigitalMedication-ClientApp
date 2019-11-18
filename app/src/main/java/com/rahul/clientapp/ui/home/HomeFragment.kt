package com.rahul.clientapp.ui.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahul.clientapp.ClinetApplication
import com.rahul.clientapp.R
import com.rahul.clientapp.adapters.MedicationAdapter
import com.rahul.clientapp.viewmodels.RoomViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var roomViewModelFactory: RoomViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onAttach(context: Context) {
        roomViewModelFactory = (activity?.application as ClinetApplication).applicationComponent.getViewModelFactory()
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val medicationAdapter = MedicationAdapter(context!!, medicationsRecyclerView)

        medicationsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = medicationAdapter
        }

        val viewModel = ViewModelProviders.of(activity!! , roomViewModelFactory).get(
            HomeViewModel::class.java)
        viewModel.medicationList.observe(this, Observer { list ->
            list?.let { medicationList ->
                if(medicationList.isEmpty())
                    return@Observer
                relaxImageView.visibility = View.GONE
                relaxTextView.visibility = View.GONE
                Handler().postDelayed({
                    if(greetingsTextView != null)
                        greetingsTextView.text = "Here's the list of your medications"
                }, 2000L)

                medicationsRecyclerView.visibility = View.VISIBLE
                medicationAdapter.swapList(medicationList)
            }
        })
    }
}