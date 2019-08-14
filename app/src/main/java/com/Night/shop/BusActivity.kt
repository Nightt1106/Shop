package com.Night.shop

import android.os.Bundle

import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;


import kotlinx.android.synthetic.main.activity_bus.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET



class BusActivity : AppCompatActivity(),AnkoLogger {

    var busses : Busses? = null
    val retrofit = Retrofit.Builder()
        .baseUrl("https://data.tycg.gov.tw/opendata/datalist/datasetMeta/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        //TODO: BUS

        doAsync {

            val bussesSevice = retrofit.create(BussesSevice::class.java)
            busses = bussesSevice.listBus()
                .execute()
                .body()

            busses?.datas?.forEach {
                info {
                    it.BusID
                    it.BusStatus
                    it.DataTime
                }
            }
            
            uiThread {

            }

        }
    }
}


data class Busses(
    val datas: List<Data>
)

data class Data(
    val Azimuth: String,
    val BusID: String,
    val BusStatus: String,
    val DataTime: String,
    val DutyStatus: String,
    val GoBack: String,
    val Latitude: String,
    val Longitude: String,
    val ProviderID: String,
    val RouteID: String,
    val Speed: String,
    val ledstate: String,
    val sections: String
)

interface BussesSevice {
    @GET("download?id=b3abedf0-aeae-4523-a804-6e807cbad589&rid=bf55b21a-2b7c-4ede-8048-f75420344aed")
    fun listBus(): Call<Busses>
}