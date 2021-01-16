package com.example.empathy_diary

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SimilarAdapter(val context: Context, val feedList: ArrayList<Item_feed>):RecyclerView.Adapter<SimilarAdapter.Holder>(){
    var feeds = feedList
    var retrofitClient = RetrofitClient()

    class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        val feedDate = itemView?.findViewById<TextView>(R.id.feed_date)
        val feedContext = itemView?.findViewById<TextView>(R.id.feed_context)
        val feedLike = itemView?.findViewById<TextView>(R.id.likes_count)
        val itemView_on = itemView

        fun bind(feed:Item_feed, context: Context){
            feedDate?.text = feed.feed_date
            feedContext?.text = feed.feed_context
            feedLike?.text = feed.feed_likes.toString()
        }
    }

    fun addItem(item : Item_feed){
        feeds.add(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return feeds.size
    }

    fun reviseItem(position: Int, value: Int){
        feeds[position].feed_likes = feeds[position].feed_likes.toInt() + value
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(feeds[position], context)
        holder.itemView_on.setOnClickListener(
                DoubleClickListener(
                        callback = object : DoubleClickListener.Callback{
                            override fun doubleClicked(){
                                val uid = FirebaseAuth.getInstance().uid!!
                                val pk = feeds[position].feed_pk
                                val call = retrofitClient.apiService.like(Like(uid, pk))
                                call!!.enqueue(object: Callback<String> {
                                    override fun onFailure(call: Call<String>, t: Throwable) {
                                        Log.d("Error", "Post Like Error")
                                    }

                                    override fun onResponse(call: Call<String>, response: Response<String>) {
                                        if(response.isSuccessful) {
                                            Log.d("Success", "Post Like Success")
                                            if(response.body().toString() == "Create"){
                                                //add 1 to likes
                                                reviseItem(position, 1)

                                            }
                                            else{ //Delete
                                                reviseItem(position, -1)
                                            }
                                        }
                                    }

                                })
                            }
                        }
                ))
    }


}