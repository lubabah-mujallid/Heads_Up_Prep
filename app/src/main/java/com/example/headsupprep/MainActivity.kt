package com.example.headsupprep

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var myList: ArrayList<Celeb.Details>
    lateinit var addButton: FloatingActionButton
    lateinit var refreshButton: FloatingActionButton
    lateinit var updateButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //supportActionBar?.hide()


        addButton = findViewById(R.id.addButton)
        refreshButton = findViewById(R.id.refreshButton)
        //updateButton = findViewById(R.id.upd)
        myList = ArrayList()

        val adapter = RecyclerAdapter(this, myList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        requestAPI()

        addButton.setOnClickListener { addPersonActivity() }
        refreshButton.setOnClickListener { requestAPI() }
    }

    private fun requestAPI() {
        val progressDialog = ProgressDialog(this@MainActivity)
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                progressDialog.setMessage("Please wait")
                progressDialog.show()
            }
            Log.d("MAIN", "fetch data")
            async { fetchData() }.await()
            if (myList.isNotEmpty()) {
                Log.d("MAIN", "Successfully got all data")
                withContext(Dispatchers.Main){
                    recyclerView.smoothScrollToPosition(myList.size-1)}
            } else {
                Log.d("MAIN", "Unable to get data")
                //Toast.makeText(this@MainActivity, "Couldn't Refresh Data, Please Try Again!", Toast.LENGTH_LONG).show()
            }
            withContext(Dispatchers.Main){
                progressDialog.dismiss() }
        }
    }

    private suspend fun fetchData() {
        Log.d("MAIN", "went inside fetch")
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<List<Celeb.Details>> = apiInterface!!.getPerson()
        val response: Response<List<Celeb.Details>>

        try {

            response = call.execute()
            withContext(Dispatchers.Main) {
                Log.d("MAIN", "fetch successful")
                for (Person in response.body()!!) {
                    updateTextView(Person)
                }
            }
        } catch (e: Exception) {
            Log.d("MAIN", "ISSUE: $e")
        }

    }

    private fun updateTextView(person: Celeb.Details) {
        myList.add(person)
        recyclerView.adapter?.notifyDataSetChanged()

    }

    private fun addPersonActivity() {
        Log.d("MAIN", "going to add activity")
        intent = Intent(applicationContext, PostActivity::class.java)
        startActivity(intent)
    }


}
/*
                myET.text.clear()
                myET.clearFocus()
                rvList.adapter?.notifyDataSetChanged()
* */
