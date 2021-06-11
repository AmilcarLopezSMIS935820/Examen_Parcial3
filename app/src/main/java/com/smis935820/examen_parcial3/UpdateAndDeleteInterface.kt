package com.smis935820.examen_parcial3

interface UpdateAndDeleteInterface {
    fun modifyItem(itemID:String, isDone:Boolean)
    fun onItemDelete(itemID: String)


}