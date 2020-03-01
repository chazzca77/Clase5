package com.miprimerapp.activitya.RecyclerView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miprimerapp.activitya.R
import com.miprimerapp.activitya.RecyclerView.Adapters.AdaptadorCustom
import com.miprimerapp.activitya.RecyclerView.Models.Platillo

class RacyclerActivity : AppCompatActivity() {

    var adaptador:AdaptadorCustom? = null
    var recyclerPlatillos:RecyclerView? = null
    var layoutManager:RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_racycler)

        recyclerPlatillos = findViewById(R.id.recyclerMenu)

        val platillos = ArrayList<Platillo>()

        platillos.add(Platillo("Platillo 1",R.drawable.platillo01))
        platillos.add(Platillo("Platillo 2",R.drawable.platillo02))
        platillos.add(Platillo("Platillo 3",R.drawable.platillo03))
        platillos.add(Platillo("Platillo 4",R.drawable.platillo04))
        platillos.add(Platillo("Platillo 5",R.drawable.platillo05))
        platillos.add(Platillo("Platillo 6",R.drawable.platillo06))
        platillos.add(Platillo("Platillo 7",R.drawable.platillo07))

        recyclerPlatillos?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        //layoutManager = GridLayoutManager(this,2)

        recyclerPlatillos?.layoutManager = layoutManager

        adaptador = AdaptadorCustom(this,platillos)

        recyclerPlatillos?.adapter = adaptador




    }
}
