package com.miprimerapp.activitya.Ubicacion

import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.internal.Objects
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.tasks.OnSuccessListener
import com.miprimerapp.activitya.R

class UbicacionActivity : AppCompatActivity() {

    private val permisoFineLocation = android.Manifest.permission.ACCESS_FINE_LOCATION
    private val permisoCoarseLocation = android.Manifest.permission.ACCESS_COARSE_LOCATION
    private val CODIGO_SOLICITUD_PERMISO = 100

    var fusedLocationClient: FusedLocationProviderClient? = null
    var locationRequest: LocationRequest? = null

    var callback: LocationCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubicacion)

        fusedLocationClient = FusedLocationProviderClient(this)
        inicializarLocationRequest()
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
                    Toast.makeText(this,"No diste permisos para la ubicación",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun obtenerUbicacion(){
        /*fusedLocationClient?.lastLocation?.addOnSuccessListener(this,object: OnSuccessListener<Location>{
            override fun onSuccess(location: Location?) {
                Toast.makeText(applicationContext,"${location?.latitude} - ${location?.longitude}",Toast.LENGTH_SHORT).show()
            }
        })*/

        callback = object: LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)

                for(ubicacion in locationResult?.locations!!){
                    Toast.makeText(applicationContext,"${ubicacion?.latitude} - ${ubicacion?.longitude}",Toast.LENGTH_SHORT).show()
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

    override fun onPause() {
        super.onPause()
        detenerActualizacionUbicacion()
    }





}
