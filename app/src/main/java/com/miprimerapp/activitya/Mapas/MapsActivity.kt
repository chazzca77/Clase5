package com.miprimerapp.activitya.Mapas

import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.miprimerapp.activitya.R

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener ,GoogleMap.OnMarkerDragListener{

    private var miPosicion:LatLng? = null

    private val permisoFineLocation = android.Manifest.permission.ACCESS_FINE_LOCATION
    private val permisoCoarseLocation = android.Manifest.permission.ACCESS_COARSE_LOCATION
    private val CODIGO_SOLICITUD_PERMISO = 100

    var fusedLocationClient: FusedLocationProviderClient? = null
    var locationRequest: LocationRequest? = null

    var callback: LocationCallback? = null

    private lateinit var mMap: GoogleMap

    //Marcadores Mapa
    private var marcadorPiramides:Marker? = null
    private var marcadorTorre:Marker? = null
    private var marcadorTepti:Marker? = null

    //OnDrag Markers
    private var listaMarcadores:ArrayList<Marker>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = FusedLocationProviderClient(this)
        inicializarLocationRequest()

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

       // mMap.mapType = GoogleMap.MAP_TYPE_HYBRID

        val PIRAMIDES = LatLng(29.9781419,31.1125828)
        val TORRE = LatLng(48.8583701,2.2922926)
        val TEOTIHUACAN = LatLng(19.6933101,-98.9421685)

        marcadorPiramides = mMap.addMarker(MarkerOptions()
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            .alpha(0.6f)
            .snippet("Somo las pirámides")
            .position(PIRAMIDES)
            .title("Pirámides"))
        marcadorPiramides?.tag = 0

        marcadorTorre = mMap.addMarker(MarkerOptions()
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            .alpha(0.6f)
            .snippet("Somos Torre")
            .position(TORRE)
            .title("Torre Eiffel"))
        marcadorTorre?.tag = 0
        marcadorTorre?.isDraggable = true

        marcadorTepti = mMap.addMarker(MarkerOptions()
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            .alpha(0.6f)
            .snippet("Teo")
            .position(TEOTIHUACAN)
            .title("Teotihuacan"))
        marcadorTepti?.tag = 0

        mMap.setOnMarkerClickListener(this)
        mMap.setOnMarkerDragListener(this)

        listaMarcadores = ArrayList()

        mMap.setOnMapLongClickListener {
            location:LatLng->

            listaMarcadores?.add(mMap.addMarker(MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .alpha(0.6f)
                .snippet("Teo")
                .position(location)
                .title("Teotihuacan")))

            listaMarcadores?.last()?.isDraggable = true

            val coordenadas = LatLng(listaMarcadores?.last()!!.position.latitude,listaMarcadores?.last()!!.position.longitude)

            val str_origin = "origin=" + miPosicion?.latitude + "," + miPosicion?.longitude;
            // Destination of route
            val str_dest = "destination=" + coordenadas.latitude + "," + coordenadas.longitude;

            // Sensor enabled
            val sensor = "sensor=true";
            val mode = "mode=driving";
            val key = "key="+getResources().getString(R.string.google_maps_key);
            // Building the parameters to the web service
            val parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode + "&" + key;

            // Output format
            val output = "json";

            // Building the url to the web service
            val url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters

            cargarUrl(url)

            //////////////////////////////NOTA
            /*
            Deben crear su api key desde el primcipio con la url en el archivo de la api key
            Quitan las restricciones que tiene por default
            entran a esta url: https://cloud.google.com/maps-platform?hl=en#get-started
            necesitan habilitar su projecto y la api key configurando  la cuenta de facturación
            ->Configurar cuenta
            ->Seleccionan las 3 opciones Maps,Rutas,Places   ->Siguiente
            ->Habilitar

            -> Copian la nueva api key y la agregan a su proyecto actual de mapas

            Prueban su api key, dene quetar asi la url
            https://maps.googleapis.com/maps/api/directions/json?origin=20.1245283,-98.7351917&destination=20.12073437354103,-98.73010452836752&sensor=true&mode=driving&key=AIzaSyCuhJPinxEEmr3cDCz5t6NkNxQ-RuiL0IY

            **/
        }

        //Polilineas PolYlines
        /*var coordenadas = PolylineOptions()
            .add(LatLng(20.12240476858759,-98.7401507422328))
            .add(LatLng(20.120529114569774,-98.73233243823053))
            .add(LatLng(20.115766841923495,-98.73582802712917))

        mMap.addPolyline(coordenadas)*/

        var coordenadasPoligono = PolygonOptions()
            .add(LatLng(20.12240476858759,-98.7401507422328))
            .add(LatLng(20.120529114569774,-98.73233243823053))
            .add(LatLng(20.115766841923495,-98.73582802712917))
            //.strokePattern(arrayListOf(Dot(),Gap(20f)))
            .strokeWidth(30f)
            .fillColor(Color.RED)
        mMap.addPolygon(coordenadasPoligono)

        var circulo = CircleOptions()
            .center(LatLng(20.12240476858759,-98.7401507422328))
            .radius(300.0)

        mMap.addCircle(circulo)
    }

    private fun inicializarLocationRequest(){
        locationRequest = LocationRequest()
        locationRequest?.interval = 10000
        locationRequest?.fastestInterval = 5000
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun validarPermisosUbicacion():Boolean{
        var hayUbicacionPrecisa = ActivityCompat.checkSelfPermission(this,permisoFineLocation) == PackageManager.PERMISSION_GRANTED
        var hayUbicacionOrdinaria = ActivityCompat.checkSelfPermission(this,permisoCoarseLocation) == PackageManager.PERMISSION_GRANTED

        return hayUbicacionPrecisa && hayUbicacionOrdinaria
    }
    private fun pedirPermisos(){
        val deboDarInfo = ActivityCompat.shouldShowRequestPermissionRationale(this,permisoFineLocation)
        if(deboDarInfo){
            //Mandar mensaje al usuario con explicación
        }else{
            solicitudPermiso()
        }
    }
    private fun solicitudPermiso(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(permisoFineLocation,permisoCoarseLocation),CODIGO_SOLICITUD_PERMISO)
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CODIGO_SOLICITUD_PERMISO ->{
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //obtenerUbicacion
                    obtenerUbicacion()
                }else{
                    Toast.makeText(this,"No diste permisos para la ubicación", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun obtenerUbicacion(){

        callback = object: LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)

                mMap.isMyLocationEnabled = true
                mMap.uiSettings.isMyLocationButtonEnabled = true

                for(ubicacion in locationResult?.locations!!){
                   // Toast.makeText(applicationContext,"${ubicacion?.latitude} - ${ubicacion?.longitude}", Toast.LENGTH_SHORT).show()

                     miPosicion = LatLng(ubicacion.latitude , ubicacion.longitude)
                    mMap.addMarker(MarkerOptions().position(miPosicion!!).title("Aquí estamos"))
                   // mMap.moveCamera(CameraUpdateFactory.newLatLng(miPosicion))



                }
            }
        }
        fusedLocationClient?.requestLocationUpdates(locationRequest,callback,null)
    }
    private fun detenerActualizacionUbicacion(){
        fusedLocationClient?.removeLocationUpdates(callback)
    }
    override fun onStart() {
        super.onStart()
        if(validarPermisosUbicacion()){
            obtenerUbicacion()
        }else{
            pedirPermisos()
        }
    }
    override fun onMarkerClick(marcador: Marker?): Boolean {
        var numeroClicks = marcador?.tag as? Int
        if(numeroClicks != null){
            numeroClicks++
            marcador?.tag = numeroClicks
            Toast.makeText(this,"Se han dado "+numeroClicks+ " clicks",Toast.LENGTH_SHORT).show()
        }
        return false
    }

    override fun onMarkerDragEnd(marcador: Marker?) {
        val index = listaMarcadores?.indexOf(marcador)
        //Log.e("Marcador Final", listaMarcadores?.get(index!!)!!.position.latitude.toString()+","+listaMarcadores?.get(index!!)!!.position.longitude.toString())
    }

    override fun onMarkerDragStart(marcador: Marker?) {
        val index = listaMarcadores?.indexOf(marcador)
        //Log.e("Marcador ININCIAL", listaMarcadores?.get(index!!)!!.position.latitude.toString()+","+listaMarcadores?.get(index!!)!!.position.longitude.toString())
    }

    override fun onMarkerDrag(marcador: Marker?) {
        val index = listaMarcadores?.indexOf(marcador)
       // Log.e("MARCADOR EN MOVIMIENTO", listaMarcadores?.get(index!!)!!.position.latitude.toString()+","+listaMarcadores?.get(index!!)!!.position.longitude.toString())
    }


    private fun cargarUrl(url:String){

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->

                val coordenadas = obtenerCoordenadas(response)
                mMap.addPolyline(coordenadas)

            },
            Response.ErrorListener { error ->
                Log.e("Volley",error.toString())  })
        // Add the request to the RequestQueue.
        queue.add(stringRequest)


        Log.e("url",url)


    }

    private fun obtenerCoordenadas(json:String):PolylineOptions{

        val gson = Gson()
        val objeto = gson.fromJson(json, com.miprimerapp.activitya.Mapas.Response::class.java)

        Log.e("respuesta",json.toString())

        val puntos = objeto.routes?.get(0)!!.legs?.get(0)!!.steps!!

        var coordenadas = PolylineOptions()

        for(punto in puntos){
            coordenadas.add(punto.start_location?.toLatLng())
            coordenadas.add(punto.end_location?.toLatLng())
        }

        coordenadas.color(Color.CYAN)
            .width(15f)

        return coordenadas

    }

}
