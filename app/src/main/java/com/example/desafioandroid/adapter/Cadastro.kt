package com.example.desafioandroid.adapter

import com.example.desafioandroid.model.Cor

class Cadastro {
    private lateinit var lista: ArrayList<Cor>

    init {
        this.lista = arrayListOf()
    }

    fun add(cor: Cor) {
        this.lista.add(cor)
    }

    fun get(): ArrayList<Cor> {
        return this.lista
    }

    fun get(index: Int): Cor {
        return this.lista.get(index)
    }

    fun count(): Int {
        return this.lista.count()
    }
}