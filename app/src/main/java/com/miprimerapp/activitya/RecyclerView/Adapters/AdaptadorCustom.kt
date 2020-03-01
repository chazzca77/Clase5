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

class AdaptadorCustom (var contexto:Context, items:ArrayList<Platillo>) : RecyclerView.Adapter<AdaptadorCustom.ViewHolder>(){

    var items: ArrayList<Platillo>? = null

    init {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorCustom.ViewHolder {

        var vista = LayoutInflater.from(contexto).inflate(R.layout.template_platillo,parent,false)
        val viewHolder = ViewHolder(vista)

        return viewHolder

    }

    override fun getItemCount(): Int {
        return items?.count()!!
    }


    override fun onBindViewHolder(holder: AdaptadorCustom.ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.foto?.setImageResource(item?.foto!!)
        holder.nombre?.text = item?.nombre
    }

    class ViewHolder(vista: View):RecyclerView.ViewHolder(vista){

        var foto:ImageView? = null
        var nombre:TextView? = null

        init {
            this.foto = vista.imgPlatillo
            this.nombre = vista.txtNombre
        }


    }



}