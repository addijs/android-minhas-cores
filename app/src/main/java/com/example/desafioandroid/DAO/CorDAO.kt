package com.example.desafioandroid.DAO

import android.content.ContentValues
import android.content.Context
import com.example.desafioandroid.DatabaseHelper
import com.example.desafioandroid.model.Cor

class CorDAO {
  private lateinit var database: DatabaseHelper

  constructor(context: Context) {
    this.database = DatabaseHelper(context)
  }

  fun insert(cor: Cor): Long {
    val contentValues = ContentValues()
    contentValues.put("nome", cor.nome)
    contentValues.put("codigo", cor.codigo)

    return this.database.writableDatabase.insert("cores", null, contentValues)
  }

  fun findAll(): ArrayList<Cor> {
    val list = ArrayList<Cor>()
    val columns = arrayOf("id", "nome", "codigo")

    val cursor = this.database.readableDatabase.query("cores", columns, null, null, null, null, "nome")
    cursor.moveToFirst()

    for (i in 1..cursor.count) {
      val id = cursor.getInt(cursor.getColumnIndex("id"))
      val nome = cursor.getString(cursor.getColumnIndex("nome"))
      val codigo = cursor.getInt(cursor.getColumnIndex("codigo"))

      list.add( Cor(id, nome, codigo) )
      cursor.moveToNext()
    }

    return list
  }

  fun findOne(id: Int): Cor? {
    val columns = arrayOf("id", "nome", "codigo")
    val where = "id = ?"
    val pWhere = arrayOf(id.toString())

    val cursor = this.database.readableDatabase.query("cores", columns, where, pWhere, null, null, null)
    cursor.moveToFirst()

    if (cursor.count == 1){
      val id = cursor.getInt(cursor.getColumnIndex("id"))
      val nome = cursor.getString(cursor.getColumnIndex("nome"))
      val codigo = cursor.getInt(cursor.getColumnIndex("codigo"))
      return Cor(id, nome, codigo)
    }

    return null
  }

  fun update(color: Cor) {
    val where = "id = ?"
    val pWhere = arrayOf(color.id.toString())

    val cv = ContentValues()
    cv.put("nome", color.nome)
    cv.put("codigo", color.codigo)
    this.database.writableDatabase.update("cores", cv, where, pWhere)
  }

  fun delete(id: Int) {
    val where = "id = ?"
    val pWhere = arrayOf(id.toString())
    this.database.writableDatabase.delete("cores", where, pWhere)
  }
}