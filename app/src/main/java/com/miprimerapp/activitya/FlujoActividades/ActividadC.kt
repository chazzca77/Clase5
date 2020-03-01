package com.miprimerapp.activitya.FlujoActividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.miprimerapp.activitya.R
import kotlinx.android.synthetic.main.activity_actividad_c.*

class ActividadC : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_c)

        val mensaje = intent.getStringExtra("MENSAJE")
        val mensajeDeA = intent.getStringExtra("MENSAJEA")
        Toast.makeText(this,mensaje, Toast.LENGTH_SHORT).show()

        btnHaciaB.setOnClickListener {
            var intent = Intent(this,
                ActividadB::class.java)
            intent.putExtra("MENSAJE",mensajeDeA)
            startActivity(intent)
        }
    }
}
