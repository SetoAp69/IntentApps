package com.excal.intent

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

import androidx.activity.result.contract.ActivityResultContracts
import com.excal.intent.data.Person

class MainActivity : AppCompatActivity(), View.OnClickListener {
    @SuppressLint("MissingInflatedId")
    private lateinit var tvResult: TextView

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result->

        if(result.resultCode==MoveForResultActivity.RESULT_CODE && result.data!=null){
            val selectedValue =
                result.data?.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE,0)
            tvResult.text="Hasil : $selectedValue"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnMoveActivity: Button = findViewById(R.id.btn_move_activity)
        btnMoveActivity.setOnClickListener(this)

        val btnMoveActivityData:Button = findViewById(R.id.btn_move_activity_data)
        btnMoveActivityData.setOnClickListener(this)

        val btnMoveWithObject:Button = findViewById(R.id.btn_move_activity_object)
        btnMoveWithObject.setOnClickListener(this)

        val btnDialPhone:Button = findViewById(R.id.btn_dial_number)
        btnDialPhone.setOnClickListener(this)

        val btnMoveForResult:Button = findViewById(R.id.btn_move_for_result)
        btnMoveForResult.setOnClickListener(this)

        tvResult=findViewById(R.id.tv_result)

    }

    override fun onClick(v:View?){
        when (v?.id){
            R.id.btn_move_activity->{
                val intent = Intent(this@MainActivity,MoveActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_move_activity_data->{
                val moveWithDataIntent = Intent(this@MainActivity, MoveWithDataActivity::class.java)
                moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_NAME, "Jim")
                moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_AGE, 10)
                startActivity(moveWithDataIntent)
            }

            R.id.btn_move_activity_object->{
                val person= Person(
                    "Seto Apriawan",
                    99,
                    "SetoApriawan@gmail.com",
                    "Klaten"
                )
                val moveWithObjectIntent = Intent(this@MainActivity, MoveWithObjectActivity::class.java)
                moveWithObjectIntent.putExtra(MoveWithObjectActivity.EXTRA_PERSON, person)
                startActivity(moveWithObjectIntent)
            }
            R.id.btn_dial_number -> {
                val phoneNumber= "0878787878778"
                val dialIntent=Intent(Intent.ACTION_DIAL,Uri.parse("tel.$phoneNumber"))
                val mapCoord = "46.414382,10.013988"
                val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("google.streetview:cbll=$mapCoord"))
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
//                startActivity(dialIntent)


            }
            R.id.btn_move_for_result ->{
                val moveForResultIntent = Intent(this@MainActivity, MoveForResultActivity::class.java)
                resultLauncher.launch(moveForResultIntent)
            }
        }
    }
}