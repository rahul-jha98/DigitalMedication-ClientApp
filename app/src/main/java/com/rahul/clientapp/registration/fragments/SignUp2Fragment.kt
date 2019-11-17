package com.rahul.clientapp.registration.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.rahul.clientapp.customuielements.ReselectableSpinner
import com.rahul.clientapp.registration.LoginActivity
import com.example.clinic.registration.fragments.SignUpHandlerFragment
import com.rahul.clientapp.R
import com.rahul.clientapp.registration.LoginActivity.Companion.height
import kotlinx.android.synthetic.main.fragment_sign_up2.*


class SignUp2Fragment : Fragment() , ReselectableSpinner.OnSpinnerCancelledListener {

    private var selectedPosition = -1
    private var allMessList = arrayOf("Male","Female","Others")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messEditText.showSoftInputOnFocus = false
        val messAdapter = ArrayAdapter<String>(context!!, R.layout.list_item_spinner_drop_down, allMessList)
        messSpinnerView.adapter = messAdapter


        messEditText.onFocusChangeListener = View.OnFocusChangeListener{_, hasFocus ->
            if(hasFocus) {
                messSpinnerView.performClick()
            }
        }

        messSpinnerView.onAnyItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                messEditText.clearFocus()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                messEditText.setText(allMessList[position])
                selectedPosition = position
                messEditText.clearFocus()
            }

        }

        button.setOnClickListener {
            val name = nameEditText.text.toString()
            val dob = emailEditText.text.toString()
            val sex = messEditText.text.toString()
            val phNo:String = phnoEditText.text.toString()


            if(name.isEmpty()){
                nameInputLayout.error = "Should not be blank"
                return@setOnClickListener
            } else {
                nameInputLayout.error = null
            }

            if(dob.isEmpty()) {
                emailInputLayout.error = "Should not be blank"
                return@setOnClickListener
            } else {
                emailInputLayout.error = null
            }

            if(sex.isEmpty()) {
                messInputLayout.error = "Should not be blank"
                return@setOnClickListener
            } else {
                messInputLayout.error = null
            }

            if(heightEditText.text.toString().isEmpty()) {
                heightInputLayout.error = "Should not be blank"
                return@setOnClickListener
            } else {
                messInputLayout.error = null
            }

            if(weightEditText.text.toString().isEmpty()) {
                weightInputLayout.error = "Should not be blank"
                return@setOnClickListener
            } else {
                messInputLayout.error = null
            }

            val height: Int = Integer.parseInt(heightEditText.text.toString())
            val weight: Int = Integer.parseInt(weightEditText.text.toString())

            LoginActivity.name = name
            LoginActivity.dob = dob
            LoginActivity.sex = sex
            LoginActivity.phNo = phNo
            LoginActivity.height = height
            LoginActivity.weight = weight

            SignUpHandlerFragment.viewPager.currentItem = 2
        }
    }

    override fun onSpinnerCancelled() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
