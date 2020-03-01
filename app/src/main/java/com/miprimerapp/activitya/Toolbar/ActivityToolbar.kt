package com.miprimerapp.activitya.Toolbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
import com.miprimerapp.activitya.R
import kotlinx.android.synthetic.main.activity_toolbar.*

class ActivityToolbar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)

        toolbar.setTitle("Mi primer título")
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)


        val itemCompartir = menu?.findItem(R.id.itemShare)
        var shareActionProvider = MenuItemCompat.getActionProvider(itemCompartir) as ShareActionProvider

        compartirIntent(shareActionProvider)

        val itemBusqueda = menu?.findItem(R.id.itemBusqueda)
        var vistaBusqueda = itemBusqueda?.actionView as SearchView

        vistaBusqueda.queryHint = "Escribe tu nombre"

        vistaBusqueda.setOnQueryTextFocusChangeListener{view , b ->
            Log.e("LISTENERFOCUS",b.toString())
        }

        vistaBusqueda.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.e("onQueryTextSubmit",query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.e("onQueryTextChange",newText.toString())
                return true
            }

        })


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.itemFavoritos -> {
                Toast.makeText(this,"Elemento añadido como favorito",Toast.LENGTH_SHORT).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun compartirIntent(shareActionProvider: ShareActionProvider){

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Este es un mensaje compartido")
        shareActionProvider.setShareIntent(intent)
    }

}
