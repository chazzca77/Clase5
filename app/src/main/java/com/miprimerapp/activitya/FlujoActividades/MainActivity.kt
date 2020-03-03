package com.miprimerapp.activitya.FlujoActividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.miprimerapp.activitya.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //Ciclo de vida de un Activity

    var nombre = "Carlos"
    val NOMBRE = "nombre"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnActividad.setOnClickListener {
            nombre = "Sergio"
            //Toast.makeText(this,nombre,Toast.LENGTH_SHORT).show()
            var intent = Intent(this,
                ActividadB::class.java)
            intent.putExtra("MENSAJE","Hacia Actividad B")
            startActivity(intent)
            //finish()
        }

        Log.e("CVA","onCreate")
    }


    //Método para recuperar estado guardado
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        nombre = savedInstanceState.getString(NOMBRE)!!
        Toast.makeText(this,nombre,Toast.LENGTH_SHORT).show()
    }

    //Método para guardar estado de nuestra actividad actual
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(NOMBRE,nombre)
    }

    override fun onStart() {
        super.onStart()
        Log.e("CVA","onStart")

    }

    override fun onResume() {
        super.onResume()
        Log.e("CVA","onResume")
        Toast.makeText(this,nombre,Toast.LENGTH_SHORT).show()

    }

    override fun onPause() {
        super.onPause()
        Log.e("CVA","onPause")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("CVA","onRestart")
    }

    override fun onStop() {
        super.onStop()
        Log.e("CVA","onStop")
    }

    override fun onDestroy() {
        Log.e("CVA","onDestroy")
        super.onDestroy()
    }


}
