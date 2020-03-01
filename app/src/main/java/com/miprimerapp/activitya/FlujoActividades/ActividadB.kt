package com.miprimerapp.activitya.FlujoActividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.miprimerapp.activitya.R
import kotlinx.android.synthetic.main.activity_actividad_b.*

class ActividadB : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_b)

        val mensaje = intent.getStringExtra("MENSAJE")
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show()

        btnC.setOnClickListener {
            var intent = Intent(this, ActividadC::class.java)
            intent.putExtra("MENSAJE","Hacia actividad C")
            intent.putExtra("MENSAJEA",mensaje)
            startActivity(intent)
        }
        btnD.setOnClickListener {
            var intent = Intent(this, ActividadD::class.java)
            intent.putExtra("MENSAJE","Hacia actividad D")
            startActivity(intent)
        }

    }



}
