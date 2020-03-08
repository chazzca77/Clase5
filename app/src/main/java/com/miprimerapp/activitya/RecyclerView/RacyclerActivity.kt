package com.miprimerapp.activitya.RecyclerView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.miprimerapp.activitya.R
import com.miprimerapp.activitya.RecyclerView.Adapters.AdaptadorCustom
import com.miprimerapp.activitya.RecyclerView.Interfaces.ClickListenerRecycler
import com.miprimerapp.activitya.RecyclerView.Models.Platillo
import kotlinx.android.synthetic.main.activity_racycler.*

class RacyclerActivity : AppCompatActivity() {

    //Necesitamos variables globales para poderlas ocupar en toda la clase
    //nuestro adaptador Personalizado (Como vimos en ListView y GridView)
    //El recyclerView
    //Un layoutManager, el cuál nos permite decirle a nuestro recyclerview que tipo de layout tendrá (List , Grid , Struggle)
    var adaptador:AdaptadorCustom? = null
    var recyclerPlatillos:RecyclerView? = null
    var layoutManager:RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_racycler)

        //IMPORTANTE///
        //Antes de agregar al xml el recyclerview necesitamos importar la libreria implementation 'androidx.recyclerview:recyclerview:1.1.0'
        //Y seleccionar androidx.recyclerview.widget.RecyclerView

        //En el caso del recyclerview es importante bindearlo de la manera findViewById, sino , nunca lo encontrará
        recyclerPlatillos = findViewById(R.id.recyclerMenu)

        //Cramos nuestro listArray y lo llenamos con nuestro modelo Platillo  (Está en la carpeta Models)
        val platillos = ArrayList<Platillo>()

        platillos.add(Platillo("Platillo 1",250.0,3.5f,R.drawable.platillo01))
        platillos.add(Platillo("Platillo 2",550.0,1.0f,R.drawable.platillo02))
        platillos.add(Platillo("Platillo 3",50.0,3.5f,R.drawable.platillo03))
        platillos.add(Platillo("Platillo 4",200.0,4.5f,R.drawable.platillo04))
        platillos.add(Platillo("Platillo 5",150.0,0.5f,R.drawable.platillo05))
        platillos.add(Platillo("Platillo 6",250.0,3.5f,R.drawable.platillo06))
        platillos.add(Platillo("Platillo 7",250.0,5f,R.drawable.platillo07))

        //Le decimos al recycelrview que va a tener un tamaño fijo (el tamaño que nosotros le pongamos a nuestro layout del recycelrview,  está en la carpeta layout-template_platillo)
        recyclerPlatillos?.setHasFixedSize(true)

        //Indicamos que tipo de layout queremos
        layoutManager = LinearLayoutManager(this)
        //layoutManager = GridLayoutManager(this,2)
        //layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL)
        recyclerPlatillos?.layoutManager = layoutManager


        //Añadimos nuestro adaptador al recyclerview
        adaptador = AdaptadorCustom(this,platillos,object :ClickListenerRecycler{
            override fun onClick(vista: View, index: Int) {
                platillos.removeAt(index)
                adaptador?.notifyDataSetChanged()
                layoutManager = GridLayoutManager(applicationContext,2)
                recyclerPlatillos?.layoutManager = layoutManager
                Toast.makeText(applicationContext,platillos.get(index).nombre,Toast.LENGTH_SHORT).show()
            }


        })

        recyclerPlatillos?.adapter = adaptador
        swipeR.setOnRefreshListener {

            for(i in 1..10000000){}
            swipeR.isRefreshing = false
            platillos.add(Platillo("Molito",150.0,0.5f,R.drawable.platillo05))
            adaptador?.notifyDataSetChanged()
        }

    }
}
