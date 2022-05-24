package com.sumon.roomdemo

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class MyRepository(private val myDao: MyDao) {

    val readPerson: LiveData<List<Person>> = myDao.readPerson()

    suspend fun insertPerson(person: Person){
        myDao.insertPerson(person)
    }

    fun searchDatabase(searchQuery: String): Flow<List<Person>> {
        return myDao.searchDatabase(searchQuery)
    }

}