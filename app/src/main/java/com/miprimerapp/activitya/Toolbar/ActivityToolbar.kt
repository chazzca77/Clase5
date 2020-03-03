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


    //ToolBar
    //Para poder personalizar nuestro toolbar debemos remover la que trae por default en el archivo styles agregando Theme.AppCompat.Light.NoActionBar
    //Creamos en nuestro xml una toolbar androidx.appcompat.widget.Toolbar con los valores que preferamos
    //Debemos crear un elemento xml menu de tipo menu en nuestra carpeta res, ahí agregaremos lo elemontos que queremos que tenga nuestr ActionBar (Toolbar)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)

        //Cambiamos título y agregamos la ActionBar a nuestra actividad
        toolbar.setTitle("Mi primer título")
        setSupportActionBar(toolbar)
    }

    //Este método como su nombre lo dice, infla el menu que nosotros creamos en res
    //Y podemos darle algunas funcionalidades a nuestros objetos
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)

        //Métodos para la Compartir en con otras APPS
        val itemCompartir = menu?.findItem(R.id.itemShare)
        var shareActionProvider = MenuItemCompat.getActionProvider(itemCompartir) as ShareActionProvider

        compartirIntent(shareActionProvider)

        //Métodos para SearchView
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

    //Este método se manda llamar cuando se presiona cualquier elemento en nuestra actionbar
    //para poder reconocer que elemento es utilizamos una estructura when para identificarlos por su id
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
