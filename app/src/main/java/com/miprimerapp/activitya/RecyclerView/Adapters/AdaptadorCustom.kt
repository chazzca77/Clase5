package com.miprimerapp.activitya.RecyclerView.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miprimerapp.activitya.R
import com.miprimerapp.activitya.RecyclerView.Interfaces.ClickListenerRecycler
import com.miprimerapp.activitya.RecyclerView.Models.Platillo
import kotlinx.android.synthetic.main.template_platillo.view.*


//Creamos nuestro adaptador Personalizado
//los parámetros enviados serán el contexto, y el arrayList de tipo Platillo
//En lugar de Heredar de un Base Adapter será : RecyclerView.Adapter<AdaptadorCustom.ViewHolder>()
//El ViewHolder es necesario en este tipo de adaptador, en un ListView o Grid no es necesario

class AdaptadorCustom (contexto:Context, items:ArrayList<Platillo> , var listener: ClickListenerRecycler) : RecyclerView.Adapter<AdaptadorCustom.ViewHolder>(){

    //Inicializamos
    var items: ArrayList<Platillo>? = null
    var contexto:Context = contexto

    init {
        this.items = items
    }

    //Este método inflamos nuestro layout creado
    //Y retronamos nuestro viewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorCustom.ViewHolder {

        var vista = LayoutInflater.from(contexto).inflate(R.layout.template_platillo,parent,false)
        val viewHolder = ViewHolder(vista,listener)

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
        holder.precio?.text = "$${item?.precio}"
        holder.rating?.rating = item?.rating!!


    }

    //Creamos nuestro ViewHolder dentro del Adaptador e inicializamos nuestro elementos del template
    class ViewHolder(vista: View,listener: ClickListenerRecycler):RecyclerView.ViewHolder(vista), View.OnClickListener{

        var foto:ImageView? = null
        var nombre:TextView? = null
        var precio:TextView? = null
        var rating:RatingBar? = null
        var listener:ClickListenerRecycler? = null

        init {
            this.foto = vista.imgPlatillo
            this.nombre = vista.txtNombre
            this.precio = vista.txtPrecio
            this.rating = vista.ratePlatillo
            this.listener = listener
            vista.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!,adapterPosition)
        }


    }



}