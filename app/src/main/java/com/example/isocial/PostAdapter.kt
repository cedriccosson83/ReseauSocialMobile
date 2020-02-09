package com.example.isocial

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.recycler_view_post_cell.view.*


class PostAdapter(val posts: ArrayList<Post>,  val clickListener: (Post) -> Unit, val clickListenerPost: (Post) -> Unit): RecyclerView.Adapter<PostAdapter.PostViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_post_cell, parent,false)
        return PostAdapter.PostViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posts.count()
    }

    override fun onBindViewHolder(holder: PostAdapter.PostViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post,clickListener, clickListenerPost)
    }

    class PostViewHolder(val view: View): RecyclerView.ViewHolder(view){
        lateinit var auth: FirebaseAuth
        val database = FirebaseDatabase.getInstance()

        //This function allows to show the name of the user
        fun showUserName(userId : String) {

            val myRef = database.getReference("users")
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot){
                    var user: User
                    for(value in dataSnapshot.children ) {
                        user = User(value.child("userid").value.toString(), value.child("email").value.toString(), value.child("firstname").value.toString(), value.child("lastname").value.toString(),value.child("birthdate").value.toString(),null,null,null)
                        if(user.userid == userId){
                            view.textViewName.text = "${user.firstname} ${user.lastname}"
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                    Log.w("post", "Failed to read value.", error.toException())
                }
            })


        }
        fun bind(post: Post, clickListener: (Post) -> Unit, clickListenerPost: (Post) -> Unit){
            //view.textViewName.text = "${post.userid}"
            view.textViewContent.text = "${post.content}"
            view.textViewDate.text = "${post.date}"
            view.textViewName.setOnClickListener { clickListener(post) }
            view.imageViewUser.setOnClickListener { clickListener(post) }
            view.cardViewPost.setOnClickListener {clickListenerPost(post) }
            showUserName(post.userid)
        }
    }




}