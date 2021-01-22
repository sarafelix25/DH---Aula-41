package com.e.aula41

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Exception

//SQLITE

class DataBaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    //aqui vamos criar as variaveis ali de cima
    companion object {
        private val DATABASE_NAME = "mywallet"
        private val DATABASE_VERSION = 1

        //cria a tabela e já diz quais as colunas e os atributos dela
        private val TABLE_GASTOS = "gastos"
        private val KEY_ID = "id"
        private val KEY_NOME = "nome"
        private val KEY_VALOR = "valor"
    }

    /*
    CREATE TABLE gastos (
        id INTEGER  PRIMARY KEY,
        nome TEXT
        valor REAL
    );
     */


    //métodos sobrescritos que vem do SQLiteOpenHelper
    //cria o banco de dados
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_GASTOS = ("CREATE TABLE $TABLE_GASTOS(" +
                "$KEY_ID INTEGER PRIMARY KEY, " +
                "$KEY_NOME TEXT, " +
                "$KEY_VALOR REAL " +
                ")")
        db?.execSQL(CREATE_TABLE_GASTOS)
    }

    //atualiza o banco de dados se tem mudança na versão
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_GASTOS")
    }


    //funçao criada por nós mesmos
    fun adGasto(gasto: Gasto): Long {
        //abre o banco no formato de leitura
        val db = this.writableDatabase

        //INSERT INTO gastos(id, nome, valor) VALUES (1, "comida", 10.2)
        val content = ContentValues()
        content.put(KEY_ID, gasto.id)
        content.put(KEY_NOME, gasto.nome)
        content.put(KEY_VALOR, gasto.valor)

        val res = db.insert(TABLE_GASTOS, null, content)
        db.close()

        return res
    }

    //função para visualizar
    fun getAllGastos() : List<Gasto> {

        val listGastos = ArrayList<Gasto>()

        //vai percorrer a tabela - DQL e pegar todos os dados
        val query = "SELECT * FROM $TABLE_GASTOS"

        //abre o banco de dados como leitura e para percorrer temos que ter o cursor
        val db = this.readableDatabase
        var cursor : Cursor? = null

        try {
            cursor = db.rawQuery(query, null)
            if(cursor.moveToFirst()){
                do {
                    //compor objeto gasto
                    val id = cursor.getInt(cursor.getColumnIndex("id"))
                    val nome = cursor.getString(cursor.getColumnIndex("nome"))
                    val valor = cursor.getDouble(cursor.getColumnIndex("valor"))

                    listGastos.add(Gasto(id, nome, valor))

                } while (cursor.moveToNext())

            }
        } catch (e: SQLException){
            Log.e("ERRO", e.toString())

        }

        return listGastos
    }

}