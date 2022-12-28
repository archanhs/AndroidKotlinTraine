package com.bcaf.training

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_portofolio.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class Portofolio : AppCompatActivity() {
    companion object {
        private val REQUEST_CODE_PERMISSION = 999;
        private val CAMERA_REQUEST_CAPTURE = 666;
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portofolio)
        imgTelp.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:082241921977")
            }
            startActivity(intent);
        })
        imgEmail.setOnClickListener(View.OnClickListener {
            composeEmail(arrayOf("archanhabib11@gmail.com","sandhyakmal21@gmail.com"),"Subject")
        })
        imgMaps.setOnClickListener(View.OnClickListener {
           showMap(Uri.parse("geo:0,0?q=kost+luhuri+2"));
        })
        imgCamera.setOnClickListener(View.OnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions,REQUEST_CODE_PERMISSION)
                } else{
                    captureCamera()
                }
            }
        })
        cardCalculator.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Calculator::class.java);
            startActivity(intent)
        })
        cardHitungUmur.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,MenuActivity::class.java);
            startActivity(intent)
        })
    }


    fun saveImage(bitMap : Bitmap){
        val tanggal = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date());
        val extStorage = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();
//        val extStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        val namaFile = extStorage+"/BCAF_"+tanggal+".png";

        var file:File? = null;
        file = File(namaFile);
        file.createNewFile();

        val bos = ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.PNG,0,bos);
        val bitmapData = bos.toByteArray();

        val fos = FileOutputStream(file);

        fos.write((bitmapData));
        fos.flush();
        fos.close();
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CAMERA_REQUEST_CAPTURE && resultCode == RESULT_OK){
            val bitmapImage = data?.extras?.get("data") as Bitmap;
            imgCamera.setImageBitmap(bitmapImage);
            saveImage(bitmapImage);
        }
    }
    fun captureCamera(){
        val takeCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takeCamera, CAMERA_REQUEST_CAPTURE);
    }
    fun composeEmail(address:Array<String>,subject:String){
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_EMAIL,address)
            putExtra(Intent.EXTRA_SUBJECT,subject)
        }
        if (intent.resolveActivity(packageManager)!=null){
            startActivity(intent);
        }

    }

    fun showMap(uri:Uri){
        val mapIntent = Intent(Intent.ACTION_VIEW,uri);
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                    captureCamera();
                }else{
                    Toast.makeText(this,"Maaf Permission Denied",Toast.LENGTH_LONG).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}