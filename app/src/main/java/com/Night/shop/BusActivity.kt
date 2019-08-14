package com.Night.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import kotlinx.android.synthetic.main.activity_bus.*
import kotlinx.android.synthetic.main.content_bus.*
import kotlinx.android.synthetic.main.row_bus_function.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET



class BusActivity : AppCompatActivity(),AnkoLogger {

    var bus : Busses? = null
    val retrofit = Retrofit.Builder()
        .baseUrl("https://data.tycg.gov.tw/opendata/datalist/datasetMeta/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus)
        setSupportActionBar(toolbar)

        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        //TODO: BUS

        doAsync {

            val bussesSevice = retrofit.create(BusSevice::class.java)
            bus = bussesSevice.listBus()
                .execute()
                .body()

            bus?.datas?.forEach {
                info {
                    it.BusID
                }
            }
            
            uiThread {
                recycler_bus.layoutManager = LinearLayoutManager(this@BusActivity)
                recycler_bus.setHasFixedSize(true)
                recycler_bus.addItemDecoration(DividerItemDecoration(this@BusActivity,DividerItemDecoration.VERTICAL))
                recycler_bus.adapter = BusAdapter()
            }

        }
    }

    inner class BusAdapter() : RecyclerView.Adapter<BusHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_bus_function,parent,false)
            return  BusHolder(view)
        }

        override fun getItemCount(): Int {
            return bus?.datas?.size ?:0
        }

        override fun onBindViewHolder(holder: BusHolder, position: Int) {
            bus!!.datas[position].let {
                holder.bindBus(it)
            }
        }
    }

    inner class BusHolder(view: View) : RecyclerView.ViewHolder(view){
        val busId = view.bus_id
        fun bindBus(bus:Data){
            busId.text = bus.BusID
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

interface BusSevice {
    @GET("download?id=b3abedf0-aeae-4523-a804-6e807cbad589&rid=bf55b21a-2b7c-4ede-8048-f75420344aed")
    fun listBus(): Call<Busses>
}