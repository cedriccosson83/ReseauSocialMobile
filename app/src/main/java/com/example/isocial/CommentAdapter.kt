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
import kotlinx.android.synthetic.main.activity_post.view.*
import kotlinx.android.synthetic.main.recycler_view_comment_cell.view.*
import kotlinx.android.synthetic.main.recycler_view_post_cell.view.*


class CommentAdapter(val comments: ArrayList<Comment>): RecyclerView.Adapter<CommentAdapter.CommentViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentAdapter.CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_comment_cell, parent,false)
        return CommentAdapter.CommentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return comments.count()
    }

    override fun onBindViewHolder(holder: CommentAdapter.CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.bind(comment)
    }


    class CommentViewHolder(val view: View): RecyclerView.ViewHolder(view){

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
                            view.textViewNameComment.text = "${user.firstname} ${user.lastname}"
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                    Log.w("post", "Failed to read value.", error.toException())
                }
            })


        }
        fun bind(comment: Comment){
            view.textViewContentComment.text = "${comment.content}"
            showDate(comment.date, view.textViewDateComment)
            showUserName(comment.userid)

        }
    }

}