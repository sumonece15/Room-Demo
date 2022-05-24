package com.sumon.roomdemo

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_table")
data class Person(
    val firstName: String = "",
    val lastName: String = "",
    val profilePhoto: String = ""
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
