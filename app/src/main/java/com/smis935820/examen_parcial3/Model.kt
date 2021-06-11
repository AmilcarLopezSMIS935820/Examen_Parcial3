package com.smis935820.examen_parcial3

class Model {
    companion object Factory{
        fun createList(): Model=Model()

    }

    var id: String? = null
    var item: String? = null
    var done: Boolean? = null


}