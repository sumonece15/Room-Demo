package com.sumon.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {


    private val adapter by lazy { MyAdapter() }
    private lateinit var myViewModel: MyViewModel
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
           val person1 = Person("Sumon", "Hossain", getBitmap())
            val person2 = Person("Sabbir", "Hossain", getBitmap())
           val person3 = Person("Jobayer", "Ahmed", getBitmap())
           //myViewModel.insertPerson(person1)
          // myViewModel.insertPerson(person2)
           //myViewModel.insertPerson(person3)
        }

        myViewModel.readPerson().observe(this, { localData ->
            if(localData.isEmpty()){
                myViewModel.getFirebaseData().observe(this, Observer {
                    adapter.setData(it)
                })
            }
            else{
                adapter.setData(localData)
            }
        })

    }

    private fun getBitmap(): String {
        return "https://avatars3.githubusercontent.com/u/14994036?s=400&u=2832879700f03d4b37ae1c09645352a352b9d2d0&v=4";
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        if (newText != null){

            searchDatabase(newText)
        }
        return true
    }

    private fun searchDatabase(query: String){

        val searchQuery = "%$query%"
        myViewModel.searchDatabase(searchQuery).observe(this, { list ->
            list.let {
                adapter.setData(it)
            }
        })
    }



}