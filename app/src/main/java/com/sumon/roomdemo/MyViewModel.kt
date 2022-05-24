package com.sumon.roomdemo

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.*
import com.sumon.firebase.FirebaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(application: Application): AndroidViewModel(application) {

    private val dao = MyDatabase.getDatabase(application).myDao()
    private val repository = MyRepository(dao)
    private lateinit var db : FirebaseFirestore

    fun readPerson(): LiveData<List<Person>>{
        val localDbData = repository.readPerson
        return localDbData
    }

    fun insertPerson(person: Person){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertPerson(person)
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Person>> {
        return repository.searchDatabase(searchQuery).asLiveData()
    }

    public fun getFirebaseData(): LiveData<List<Person>> {

        val personRemoteData: MutableLiveData<List<Person>> = MutableLiveData()
        val firebaseData:ArrayList<Person> = ArrayList()

        db = FirebaseFirestore.getInstance()
        db.collection("Users").addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("FireStore Error", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            val person = dc.document.toObject(Person::class.java)
                            firebaseData.add(person)
                        }
                    }
                    personRemoteData.value = firebaseData
                }
            })

        return personRemoteData
    }


}