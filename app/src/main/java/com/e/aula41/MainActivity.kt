package com.e.aula41

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var dataBaseHandler: DataBaseHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //tem que fazer com ViewModel

        dataBaseHandler = DataBaseHandler(this)
//
//        val gasto = Gasto(6, "café", 10.0)
//        val res = dataBaseHandler.adGasto(gasto)
//        Log.i("MAIN ACTIVITY", res.toString())

        val listGasto = dataBaseHandler.getAllGastos()
        listGasto.forEach{
            Log.i("MAIN ACTIVITY", it.toString())
        }

    }

}