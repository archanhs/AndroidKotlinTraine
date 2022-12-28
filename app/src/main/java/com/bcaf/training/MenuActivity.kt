package com.bcaf.training

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_menu.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*

class MenuActivity : AppCompatActivity() {
     var username:String = "";
     var password:String = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val datas:Bundle? =intent.extras;
        username = datas?.getString("username","").toString();
        password = datas?.getString("password","").toString()
        txtHello.text = "Hallo, Admin"
        animateTest();
        btnTanggalLahir.setOnClickListener(View.OnClickListener {
            pickDate();
        })
        btnDial.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:082241921977")
            }
            startActivity(intent);
        })

    }
    fun animateTest(){
        val anim = AlphaAnimation(0.0f,1f);
        anim.duration = 200;
        anim.startOffset = 1000;
        anim.repeatMode = Animation.REVERSE;
        anim.repeatCount = Animation.INFINITE;
        txtHello.startAnimation(anim);
    }
    fun pickDate(){
        val  c = Calendar.getInstance();

        val dateSetListener = object : DatePickerDialog.OnDateSetListener{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDateSet(
                view:DatePicker,year: Int,monthOfYear: Int,dayOfMonth: Int
            ){
                c.set(Calendar.YEAR,year)
                c.set(Calendar.MONTH,monthOfYear)
                c.set(Calendar.DAY_OF_MONTH,dayOfMonth)

                val myFormat = "dd/MMM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
                txtTanggalLahir.text = sdf.format(c.getTime());
                var age = Period.between( LocalDate.of(year, monthOfYear, dayOfMonth),LocalDate.now())
//                var month = Period.between( LocalDate.of(Calendar.YEAR, monthOfYear, Calendar.DAY_OF_MONTH),LocalDate.now())
//                var age = getAge(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
                txtAge.setText("Umur anda adalah ${age.years} tahun ${age.months} bulan");

            }
        }

        DatePickerDialog(this,dateSetListener,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show() //parameter c.get adalah inisialisasi awal kalender yaitu hari ini
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getAge(year: Int, month: Int, dayOfMonth: Int): Int {
        return Period.between(
            LocalDate.of(year, month, dayOfMonth),
            LocalDate.now()
        ).years
    }
}