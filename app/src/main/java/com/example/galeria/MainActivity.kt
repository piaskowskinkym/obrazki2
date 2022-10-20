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
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PorterDuff
import android.widget.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var ukryj = findViewById<CheckBox>(R.id.checkBox);
        var obrazek = findViewById<ImageView>(R.id.imageView)

        var ibKamera = findViewById<ImageButton>(R.id.ibKamera)
        var edRotacja = findViewById<EditText>(R.id.rotacja)
        var bedytuj = findViewById<Button>(R.id.bedytuj)
        var slider = findViewById<SeekBar>(R.id.seekBar)
        var slider2 = findViewById<SeekBar>(R.id.seekBar2)
        var red = findViewById<ToggleButton>(R.id.tBred)
        var blue = findViewById<ToggleButton>(R.id.tBblue)
        var green = findViewById<ToggleButton>(R.id.tBgreen)





        ibKamera.isEnabled = false




        slider.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                    obrazek.rotation = progress.toFloat()
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }
            }
        )

        slider2.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

                override fun onProgressChanged(p0: SeekBar?, progress1: Int, p2: Boolean) {
                   obrazek.imageAlpha = progress1
                }

            })


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

            if(edRotacja.getText().isEmpty() ) {
                Toast.makeText( this,"wpisz dane!",
                    Toast.LENGTH_LONG).show();

            }else {

                val nowys: String = edRotacja.getText().toString()
                val nowyi = nowys.toInt()

                obrazek.requestLayout()
                obrazek.getLayoutParams().height = nowyi
                obrazek.getLayoutParams().width = nowyi
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

      red.setOnCheckedChangeListener { compoundButton, isChecked ->
          if (blue.isChecked || green.isChecked) {
              Toast.makeText( this, " Odznacz niebieski lub zielony!",
                  Toast.LENGTH_LONG).show();

          }else{
              if(isChecked){
                  obrazek.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY)
              }else{
                  obrazek.clearColorFilter()
              }
          }
      }
        blue.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (red.isChecked || green.isChecked) {
                Toast.makeText( this, " Odznacz czerwony lub zielony!",
                    Toast.LENGTH_LONG).show();

            }else{
                if(isChecked){
                    obrazek.setColorFilter(Color.BLUE,PorterDuff.Mode.OVERLAY)
                }else{
                    obrazek.clearColorFilter()
                }
            }
        }
        green.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (blue.isChecked || red.isChecked) {
                Toast.makeText( this, " Odznacz czerwony lub niebieski!",
                    Toast.LENGTH_LONG).show();

            }else{
                if(isChecked){
                    obrazek.setColorFilter(Color.GREEN,PorterDuff.Mode.OVERLAY)
                }else{
                    obrazek.clearColorFilter()
                }
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