package com.miprimerapp.activitya.RecyclerView.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miprimerapp.activitya.R
import com.miprimerapp.activitya.RecyclerView.Models.Platillo
import kotlinx.android.synthetic.main.template_platillo.view.*


//Creamos nuestro adaptador Personalizado
//los parámetros enviados serán el contexto, y el arrayList de tipo Platillo
//En lugar de Heredar de un Base Adapter será : RecyclerView.Adapter<AdaptadorCustom.ViewHolder>()
//El ViewHolder es necesario en este tipo de adaptador, en un ListView o Grid no es necesario

class AdaptadorCustom (var contexto:Context, items:ArrayList<Platillo>) : RecyclerView.Adapter<AdaptadorCustom.ViewHolder>(){

    //Inicializamos
    var items: ArrayList<Platillo>? = null

    init {
        this.items = items
    }

    //Este método inflamos nuestro layout creado
    //Y retronamos nuestro viewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorCustom.ViewHolder {

        var vista = LayoutInflater.from(contexto).inflate(R.layout.template_platillo,parent,false)
        val viewHolder = ViewHolder(vista)

        return viewHolder

    }

    //Regresa el total de los elementos
    override fun getItemCount(): Int {
        return items?.count()!!
    }

    //Aquí asignamos los valores que queremos que tengan cada unos de los elementos del recyclerview
    override fun onBindViewHolder(holder: AdaptadorCustom.ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.foto?.setImageResource(item?.foto!!)
        holder.nombre?.text = item?.nombre
    }

    //Creamos nuestro ViewHolder dentro del Adaptador e inicializamos nuestro elementos del template
    class ViewHolder(vista: View):RecyclerView.ViewHolder(vista){

        var foto:ImageView? = null
        var nombre:TextView? = null

        init {
            this.foto = vista.imgPlatillo
            this.nombre = vista.txtNombre
        }


    }



}