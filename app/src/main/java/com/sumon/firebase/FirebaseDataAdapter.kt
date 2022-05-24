package com.sumon.firebase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sumon.roomdemo.R

class FirebaseDataAdapter(private val context: Context, private val userList: ArrayList<FirebaseModel>) : RecyclerView.Adapter<FirebaseDataAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirebaseDataAdapter.MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FirebaseDataAdapter.MyViewHolder, position: Int) {


        val firebaseModel : FirebaseModel = userList[position]

        val into = Glide.with(context)
            .load(firebaseModel.profilePhoto).into(holder.img_url)


        holder.firstName.text = firebaseModel.firstName
        holder.lastName.text = firebaseModel.lastName



    }

    override fun getItemCount(): Int {

       return userList.size
    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val firstName : TextView = itemView.findViewById(R.id.firstName_txt)
        val lastName : TextView = itemView.findViewById(R.id.lastName_txt)
        val img_url : ImageView = itemView.findViewById(R.id.imageView)
    }
}