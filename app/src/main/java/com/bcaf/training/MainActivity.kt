package com.bcaf.training

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var container:LinearLayout;
    val defaultPassword = "Admin123";
    val defaultUsername = "Admin";
    var counter = 0;

    fun init(){
        container = findViewById(R.id.containerDummy);

        btnLogin.setOnClickListener(View.OnClickListener {
            checkAuth(it);
//            var button = Button(applicationContext);
//            button.text = "hello-" + counter++;
//            container.addView(button);

        })
        txtForgot.setOnClickListener(View.OnClickListener {
            finish();
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init();
    }

    fun checkAuth(v:View){
        if(txtUsername.text.contentEquals(defaultUsername)&&txtPassword.text.contentEquals(defaultPassword)) {
            Toast.makeText(applicationContext,"Berhasil login",Toast.LENGTH_LONG).show()
            val intent = Intent(this,Portofolio::class.java);
            intent.putExtra("username",txtUsername.text.toString());
            intent.putExtra("password",txtPassword.text.toString());
            startActivity(intent)

        }else{
            Toast.makeText(applicationContext,"Username atau Password tidak sesuai",Toast.LENGTH_LONG).show()

        }
    }
    fun forgotPassword(v:View){
        Toast.makeText(applicationContext,"Forgot Password terbuka",Toast.LENGTH_LONG).show()
    }

    fun distanceInKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60 * 1.1515
        dist = dist * 1.609344
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }
}