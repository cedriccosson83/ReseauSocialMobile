package com.example.isocial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        fun bind(post: Post, clickListener: (Post) -> Unit, clickListenerPost: (Post) -> Unit){
            view.textViewName.text = "${post.user?.firstName} ${post.user?.lastName}"
            view.textViewContent.text = "${post.textContent}"
            view.textViewName.setOnClickListener { clickListener(post) }
            view.imageViewUser.setOnClickListener { clickListener(post) }
            view.setOnClickListener {clickListenerPost(post) }
            //view.setOnClickListener { clickListener(post)}
        }
    }


}