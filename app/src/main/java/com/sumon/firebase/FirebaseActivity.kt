package com.sumon.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.sumon.roomdemo.R

class FirebaseActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var firebaseModelArrayList: ArrayList<FirebaseModel>
    private lateinit var firebaseAdapter: FirebaseDataAdapter
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        firebaseModelArrayList = arrayListOf()

        firebaseAdapter = FirebaseDataAdapter(applicationContext, firebaseModelArrayList)
        recyclerView.adapter = firebaseAdapter

        EventChangeListener()

    }


    private fun EventChangeListener() {

        db = FirebaseFirestore.getInstance()
        db.collection("Users")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                    if (error != null) {

                        Log.e("FireStore Error", error.message.toString())
                        return
                    }

                    for (dc: DocumentChange in value?.documentChanges!!) {

                        if (dc.type == DocumentChange.Type.ADDED) {

                            firebaseModelArrayList.add(dc.document.toObject(FirebaseModel::class.java))
                        }
                    }

                    println(firebaseModelArrayList.size)

                    firebaseAdapter.notifyDataSetChanged()
                }

            })
    }
}