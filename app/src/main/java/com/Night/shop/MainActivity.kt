package com.Night.shop

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val RC_NICKNAME = 210
    private val RC_SIGNUP = 200

    override fun onCreate(savedInstanceState: Bundle?) {

        var signup = false

        val auth = FirebaseAuth.getInstance()

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

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
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
        nick.text = getNickName()
        Log.d("MainActivity",getNickName() )
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
