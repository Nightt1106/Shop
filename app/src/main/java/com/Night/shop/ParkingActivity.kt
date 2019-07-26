package com.Night.shop

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_parking.*
import org.jetbrains.anko.*
import java.net.URL

class ParkingActivity : AppCompatActivity() , AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking)
        val parking = "http://data.tycg.gov.tw/opendata/datalist/datasetMeta/download?id=f4cc0b12-86ac-40f9-8745-885bddc18f79&rid=0daad6e6-0632-44f5-bd25-5e1de1e9146f"

        //Anko
        doAsync {
            val url = URL(parking)
            var json= url.readText()
            info(json)
            uiThread {
                //                  Toast.makeText(it,"Got it",Toast.LENGTH_LONG).show()
                toast("Got it")
                info.text = json
                parseGson(json)
            }
        }


//        ParkingTask().execute(parking)
    }

    private  fun parseGson(json:String){
        val parking : Parking = Gson().fromJson<Parking>(json,Parking::class.java)
        info(parking.parkingLots.size)
        parking.parkingLots.forEach {
            info("${it.areaId}  ${it.areaName}  ${it.totalSpace}")
        }
    }

    //AsyncTask
    inner class  ParkingTask : AsyncTask<String,Void,String>() {
        override fun doInBackground(vararg params: String?): String {
            val url = URL(params[0])
            val json : String = url.readText()
            Log.d("ParkingActivity","doInBackground $json")
            return  json
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Toast.makeText(this@ParkingActivity,"Got it",Toast.LENGTH_LONG).show()
            info.text = result
        }
    }
}

data class Parking(
    val parkingLots: List<ParkingLot>
)

data class ParkingLot(
    val address: String,
    val areaId: String,
    val areaName: String,
    val introduction: String,
    val parkId: String,
    val parkName: String,
    val payGuide: String,
    val surplusSpace: String,
    val totalSpace: Int,
    val wgsX: Double,
    val wgsY: Double
)
