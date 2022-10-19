package com.example.galeria

import android.R.attr
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import java.util.jar.Manifest
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.Matrix;

import android.R.attr.pivotY

import android.R.attr.pivotX

import android.R.attr.angle
import android.app.ActionBar
import android.widget.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var ukryj = findViewById<CheckBox>(R.id.checkBox);
        var obrazek = findViewById<ImageView>(R.id.imageView)

        var ibKamera = findViewById<ImageButton>(R.id.ibKamera)
        var edRotacja = findViewById<EditText>(R.id.rotacja)
        var edObrot = findViewById<EditText>(R.id.obrot)
        var bedytuj = findViewById<Button>(R.id.bedytuj)
        var licznik = 1

        ibKamera.isEnabled = false


        //sprawdzanie czy mamy pozwolenie na kamere
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                111
            )
        } else
            ibKamera.isEnabled = true

        ibKamera.setOnClickListener {
            var i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(i, 101)
        }

        bedytuj.setOnClickListener {

            if(edRotacja.getText().isEmpty() || edObrot.getText().isEmpty()) {
                Toast.makeText( this,"wpisz dane!",
                    Toast.LENGTH_LONG).show();

            }else {


                val nowys: String = edRotacja.getText().toString()
                val nowyi = nowys.toInt()
                val nowys2: String = edObrot.getText().toString()
                val nowyi2 = nowys2.toFloat()

                obrazek.requestLayout()
                obrazek.getLayoutParams().height = nowyi
                obrazek.getLayoutParams().width = nowyi
                obrazek.setRotation(nowyi2)
            }
        }


        //ukrywanie obrazków
        ukryj.setOnClickListener {
            if (ukryj.isChecked()) {
                obrazek.setVisibility(View.INVISIBLE)
            } else {
                obrazek.setVisibility(View.VISIBLE)
            }
        }
        //ruch w prawo

        //ruch w lewo

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==101)
        {

            var zdjecie: Bitmap?
            zdjecie = data?.getParcelableExtra("data")
            findViewById<ImageView>(R.id.imageView).setImageBitmap(zdjecie)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==111&&grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            setContentView(R.layout.activity_main)
            ibKamera.isEnabled = true
        }
    }

}