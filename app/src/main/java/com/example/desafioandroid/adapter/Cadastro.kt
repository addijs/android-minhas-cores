package com.example.desafioandroid.adapter

import com.example.desafioandroid.model.Cor

class Cadastro {
  private lateinit var list: ArrayList<Cor>

  init {
    this.list = arrayListOf()
  }

  fun add(color: Cor) {
    this.list.add(color)
  }

  fun set(newList: ArrayList<Cor>) {
    this.list = newList
  }

  fun get(): ArrayList<Cor> {
    return this.list
  }

  fun update(color: Cor, index: Int) {
    this.list[index] = color
  }

  fun remove(index: Int) {
    this.list.removeAt(index)
  }

  fun get(index: Int): Cor {
    return this.list[index]
  }

  fun count(): Int {
    return this.list.count()
  }
}