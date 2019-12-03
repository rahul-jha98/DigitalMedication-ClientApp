package com.rahul.clientapp.ui.userdetails


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rahul.clientapp.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_user_details.*


class UserDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity!!.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        textName.text = sharedPref.getString(getString(R.string.client_name), "No Name")

        textName.text = sharedPref.getString(getString(R.string.client_name),"Data Not Found")
        textDob.text = sharedPref.getString(getString(R.string.dob),"Data Not Found")
        textPhno.text = sharedPref.getString(getString(R.string.phno),"Data Not Found")
        textHeight.text = sharedPref.getInt(getString(R.string.height),150 ).toString()
        textWeight.text = sharedPref.getInt(getString(R.string.weight),150).toString()

    }


}
