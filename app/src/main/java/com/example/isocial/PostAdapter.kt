package com.example.isocial

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.recycler_view_post_cell.view.*
import kotlin.collections.ArrayList


class PostAdapter(val posts: ArrayList<Post>,  val clickListener: (Post) -> Unit, val clickListenerPost: (Post) -> Unit, val clickListenerLike: (Post) -> Unit): RecyclerView.Adapter<PostAdapter.PostViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_post_cell, parent, false)
        return PostAdapter.PostViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posts.count()
    }

    override fun onBindViewHolder(holder: PostAdapter.PostViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post,clickListener, clickListenerPost, clickListenerLike)
    }

    class PostViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        lateinit var auth: FirebaseAuth
        val database = FirebaseDatabase.getInstance()

        //This function allows to show the name of the user
        fun showUserName(userId: String) {

            val myRef = database.getReference("users")
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var user: User
                    for (value in dataSnapshot.children) {
                        user = User(
                            value.child("userid").value.toString(),
                            value.child("email").value.toString(),
                            value.child("firstname").value.toString(),
                            value.child("lastname").value.toString(),
                            value.child("birthdate").value.toString(),
                            null,
                            null,
                            null
                        )
                        if (user.userid == userId) {
                            view.textViewName.text = "${user.firstname} ${user.lastname}"
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                    Log.w("post", "Failed to read value.", error.toException())
                }
            })


        }

        fun countLikes(post: Post) {

            var array: ArrayList<String> = post.likes
            var count: Int = array.size
            view.textViewLikeNumber.text="Likes(${count})"
            Log.d("like", array.toString())

        }

        fun countComments(postId: String) {

            val myRef = database.getReference("comments")
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val comments: ArrayList<Comment> = ArrayList<Comment>()
                    for (value in dataSnapshot.children) {


                        var comment: Comment = Comment(
                            value.child("userid").value.toString(),
                            value.child("postId").value.toString(),
                            "date def",
                            value.child("content").value.toString(),
                            null
                        )
                        if (comment.postId == postId) {
                            comments.add(comment)
                        }

                    }
                    var count: Int = comments.size
                    view.textViewCommentsNumber.text = "Commentaires(${count})"

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("comment", "Failed to read value.", error.toException())
                }
            })


        }

        fun showLike(post: Post){
            auth = FirebaseAuth.getInstance()
            val currentUserID = auth.currentUser?.uid
            val likes = post.likes
            Log.d("like", likes.toString())
            if(likes?.all { it != currentUserID } == true) {
                //likes.add(currentUserID ?: "")
                view.imageViewStar.setImageResource(R.drawable.like)
            }else{
                //likes.remove(currentUserID)
                view.imageViewStar.setImageResource(R.drawable.dislike)

            }
        }
        fun bind(post: Post, clickListener: (Post) -> Unit, clickListenerPost: (Post) -> Unit, clickListenerLike: (Post) -> Unit){
            //view.textViewName.text = "${post.userid}"
            view.textViewContent.text = "${post.content}"
            showDate(post.date, view.textViewDate)
            view.textViewName.setOnClickListener { clickListener(post) }
            view.imageViewUserPost.setOnClickListener { clickListener(post) }
            view.cardViewPost.setOnClickListener {clickListenerPost(post) }
            view.imageViewStar.setOnClickListener { clickListenerLike(post) }
            showUserName(post.userid)
            showLike(post)
            countComments(post.postid)
            countLikes(post)
        }
    }


}