package com.Night.shop

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_function.view.*

class MainActivity : AppCompatActivity() {

    private val RC_NICKNAME = 210
    private val RC_SIGNUP = 200

    val auth = FirebaseAuth.getInstance()

    val functions : List<String> = listOf<String>("Contact","Parking","Movie","Bus","E","F","G","H","I","K")

    override fun onCreate(savedInstanceState: Bundle?) {

//        var signup = false


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        /*if(!signup){
            var intent = Intent(this,SignUpActivity::class.java)
            startActivityForResult(intent,RC_SIGNUP)
        }*/

        auth.addAuthStateListener { auth->
            authChange(auth)
        }

        //Spinner
        val colors : Array<String> = arrayOf("Red","Green","Blue")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,colors)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.d("MainActivity","onItemSelected: ${colors[position]}")
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        //RecyclerView
        //TODO: recycler
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)
        recycler.adapter = FunctionAdapter()

        //-----------------
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    //!---------------------

    inner class FunctionAdapter : RecyclerView.Adapter<FunctionHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_function,parent,false)
            val holder = FunctionHolder(view)
            return holder
        }

        override fun getItemCount(): Int {
            return functions.size
        }

        override fun onBindViewHolder(holder: FunctionHolder, position: Int) {
            holder.nameText.text = functions.get(position)
            holder.itemView.setOnClickListener{
                HolderOnClickFuncion(holder,position)
            }
        }

    }

    class FunctionHolder(view:View) : RecyclerView.ViewHolder(view){
        var nameText : TextView = view.name
    }

    private fun HolderOnClickFuncion(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("MainActivity","HolderOnClickFunction:$position")
        when(position){
            0 -> startActivity(Intent(this,ContactActivity::class.java))
            1 -> startActivity(Intent(this, ParkingActivity::class.java))
            2 -> startActivity(Intent(this, MovieActivity::class.java))
            3 -> startActivity(Intent(this,BusActivity::class.java))
        }
    }


    //!---------------------


    private fun authChange(auth: FirebaseAuth) {
        if(auth.currentUser == null){
            val intent = Intent(this,SignUpActivity::class.java)
            startActivityForResult(intent,RC_SIGNUP)
        } else {
            Log.d("MainActivity","Current User: ${auth.currentUser?.uid}")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if( requestCode == RC_SIGNUP){
            if(resultCode == Activity.RESULT_OK){
                val intent = Intent(this,NickNameActivity::class.java)
                startActivityForResult(intent,RC_NICKNAME)
            }
        }
        if( requestCode == RC_NICKNAME){

        }
    }

    //TODO: BUG
    override fun onResume() {
        super.onResume()
        /*nick.text = getNickName()
        Log.d("MainActivity",getNickName() )*/

        FirebaseDatabase.getInstance()
            .getReference("users")
            .child(auth.currentUser!!.uid)
            .child("nickname")
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    nick.text = dataSnapshot.value as String //To change body of created functions use File | Settings | File Templates.
                }

            })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
