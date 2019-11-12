package com.rahul.clientapp.ui.medications

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahul.clientapp.ClinetApplication
import com.rahul.clientapp.R
import com.rahul.clientapp.adapters.MedicationAdapter
import com.rahul.clientapp.models.Medication
import com.rahul.clientapp.models.Medicine
import com.rahul.clientapp.viewmodels.RoomViewModelFactory
import kotlinx.android.synthetic.main.fragment_dashboard.*

class MedicationFragment : Fragment() {

    private lateinit var roomViewModelFactory: RoomViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
//        medicationViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        return root
    }


    override fun onAttach(context: Context) {
        roomViewModelFactory = (activity?.application as ClinetApplication).applicationComponent.getViewModelFactory()
        postponeEnterTransition()
        super.onAttach(context)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val medicationAdapter = MedicationAdapter(context!!, medicationsRecyclerView)

        medicationsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = medicationAdapter
        }

        val viewModel = ViewModelProviders.of(activity!! , roomViewModelFactory).get(MedicationViewModel::class.java)
        viewModel.medicationList.observe(this, Observer { list ->
            list?.let { medicationList ->
                medicationAdapter.swapList(medicationList)
                startPostponedEnterTransition()
            }
        })
    }
}