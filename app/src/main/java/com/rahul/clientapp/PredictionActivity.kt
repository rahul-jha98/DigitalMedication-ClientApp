package com.rahul.clientapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.RenderScript
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.children
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_prediction.*
import org.json.JSONArray
import org.json.JSONObject

class PredictionActivity : AppCompatActivity() {

    val corresponding = HashMap<String, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prediction)

        val allDiseases = arrayListOf<String>("itching",
            "skin rash",
            "chills",
            "joint pain",
            "vomiting",
            "fatigue",
            "weight loss",
            "lethargy",
            "cough",
            "high fever",
            "breathlessness",
            "sweating",
            "headache",
            "yellowish skin",
            "dark urine",
            "nausea",
            "loss of appetite",
            "abdominal pain",
            "diarrhoea",
            "mild fever",
            "yellowing of eyes",
            "swelled lymph nodes",
            "malaise",
            "blurred and distorted vision",
            "phlegm",
            "chest pain",
            "excessive hunger",
            "loss of balance",
            "irritability",
            "muscle pain")

        for(i in 0 until allDiseases.size) {
            val chip =  LayoutInflater.from(this).inflate(R.layout.chip_item, predictionChipGroup, false) as Chip
            chip.text = allDiseases[i]
            corresponding.put(allDiseases[i], 0)
            chip.setOnCheckedChangeListener { compoundButton, isChecked ->
                if(isChecked) {
                    corresponding.put(compoundButton.text.toString(), 1)
                } else
                    corresponding.put(compoundButton.text.toString(), 0)
            }

            predictionChipGroup.addView(chip)
        }

        getButton.setOnClickListener {
            TransitionManager.beginDelayedTransition(mainLayout)
            startImageView.visibility = View.GONE
            startTextView.visibility = View.GONE
            predictionChipGroup.visibility = View.GONE
            endTextView.visibility = View.VISIBLE
            endImageView.visibility = View.VISIBLE
            endProgressBar.visibility = View.VISIBLE
            getButton.visibility = View.GONE

            AndroidNetworking.post("https://b835965d.ngrok.io/api").addJSONObjectBody(JSONObject(corresponding as Map<String, Int>)).setPriority(
                RenderScript.Priority.MEDIUM)
                .build().getAsJSONArray(object: JSONArrayRequestListener {
                    override fun onResponse(response: JSONArray?) {
                        response?.let{
                            endProgressBar.visibility = View.GONE
                            endTextView.text = "Our analysis says you might suffer from "

                            mainDiseaseTextView.text = (it[0] as JSONObject).getString("name")
                            val data = (((it[0] as JSONObject).getDouble("prob") * 10000).toInt())/100.0

                            surityTextView.text = "with a probability of " + data + " %"

                            secondTextView.text =  (it[1] as JSONObject).getString("name") + " with a probability of " + (((it[1] as JSONObject).getDouble("prob") * 10000).toInt())/100.0 + " %"
                            thirdTextView.text =  (it[2] as JSONObject).getString("name") + " with a probability of " + (((it[2] as JSONObject).getDouble("prob") * 10000).toInt())/100.0 + " %"
                            mainDiseaseTextView.visibility = View.VISIBLE
                            surityTextView.visibility = View.VISIBLE
                            nextBestTextView.visibility = View.VISIBLE
                            secondTextView.visibility = View.VISIBLE
                            thirdTextView.visibility = View.VISIBLE
                        }
                    }


                    override fun onError(anError: ANError?) {
                        Toast.makeText(this@PredictionActivity, "Internet Error", Toast.LENGTH_SHORT).show()
                    }
                });
        }
    }
}
