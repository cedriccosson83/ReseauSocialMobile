package com.example.isocial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_feed.*


class FeedActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    val database = FirebaseDatabase.getInstance()

    val postsList : ArrayList<Post> = ArrayList()

    /*var user : User = User("ae1z212", "florentricciardi@gmail.com","Ricciardi","Florent", "06/03/1997","04/02/2020","01/02/2020")
    var user2 : User = User("dsq121", "cedriccosson@gmail.com", "Cosson","Cédric", "13/11/1997", "04/02/2020","02/01/2020")
    var user3 : User = User("dsqd455", "luciegaire@gmail.com", "Gaire", "Lucie","18/05/1999", "04/02/2020","18/01/2020")

    var post : Post = Post(user, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut dictum volutpat interdum. Donec sed diam nec risus tincidunt aliquet. Curabitur sed.")
    var post2 : Post = Post(user2, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut dictum volutpat interdum. Donec sed diam nec risus tincidunt aliquet. Curabitur sed.")
    var post3 : Post = Post(user3, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut dictum volutpat interdum. Donec sed diam nec risus tincidunt aliquet. Curabitur sed.")
*/
    //val tabPosts = arrayListOf(post,post2,post3)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        auth = FirebaseAuth.getInstance()

        Log.d("TAG", "TEST")
        //val posts_list : ArrayList<Post> = getPosts()
        //recyclerViewFeed.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        //recyclerViewFeed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        //recyclerViewFeed.adapter = PostAdapter(tabPosts,  { postItem : Post -> postItemClicked(postItem) }, { postItem : Post -> postClicked(postItem) } )

        getPosts()
        Log.d("TAGGUEULE", postsList.toString())
        //recyclerViewFeed.adapter = PostAdapter(postsList,
        //    { postItem : Post -> postItemClicked(postItem) },
        //    { postItem : Post -> postClicked(postItem) })

    }

    private fun getPosts() {
        Log.d("GETPOST1", "OK")
        val dbPosts = database.getReference("users")
        Log.d("GETPOST2", "OK")
        dbPosts.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val posts : ArrayList<User> = ArrayList()
                Log.d("GETPOST3", "OK")
                for (productSnapshot in dataSnapshot.children) {
                    val post = productSnapshot.getValue(User::class.java)!!
                    /*post?.let {
                        posts.add(it)
                        Log.d("CONTENU : ", it.content)
                    }*/
                    posts.add(post)
                    Log.d("CONTENU : ", post.toString())
                }
                Log.d("GETPOST4", "OK")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("ERROR : ", "CANCELLED ERROR")
                throw databaseError.toException()
            }
        })
        //return postsList
    }

    private fun postItemClicked(postItem : Post) {
        val intent = Intent(this, UserActivity::class.java)
        intent.putExtra("user", postItem.userid)
        val author = postItem.getUser()
        startActivity(intent)
        Toast.makeText(this, "Clicked: ${author.firstname} ${author.email} ", Toast.LENGTH_LONG).show()
    }

    private fun postClicked(postItem : Post) {
        val intent = Intent(this, PostActivity::class.java)
        intent.putExtra("user", postItem.userid)
        //intent.putExtra("post", post)

        val author = postItem.getUser()
        startActivity(intent)
        Toast.makeText(this, "Clicked: ${author.firstname} ${postItem.content} ", Toast.LENGTH_LONG).show()
    }
}
