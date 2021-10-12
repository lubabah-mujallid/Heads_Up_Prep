package com.example.headsupprep

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_layout.view.*

class RecyclerAdapter (val context: Context, val messages: ArrayList<Celeb.Details>):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        return RecyclerAdapter.ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.recycler_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val message = messages[position]
        val con = context
        val name = message.name.toString()
        val t1 = message.taboo1.toString()
        val t2 = message.taboo2.toString()
        val t3 = message.taboo3.toString()
        val id:Int = message.pk.toString().toInt()

        holder.itemView.apply {
            tvRecyclerName.text = name
            tvRecyclerT1.text = t1
            tvRecyclerT2.text = t2
            tvRecyclerT3.text = t3
            recyclerSearchButton.setOnClickListener {
                Log.d("RV", "going to update activity")
                val intent = Intent(con, UpdateActivty::class.java)
                intent.putExtra("name",name)
                intent.putExtra("t1", t1)
                intent.putExtra("t2", t2)
                intent.putExtra("t3", t3)
                intent.putExtra("id", id)
                Log.d("RV", "id is $id")
                Log.d("RV", "intent is $intent")
                con.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = messages.size


}