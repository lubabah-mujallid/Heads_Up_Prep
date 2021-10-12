package com.example.headsupprep

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response

class UpdateActivty : AppCompatActivity() {
    lateinit var tvNum: TextView
    lateinit var etName: EditText
    lateinit var et1: EditText
    lateinit var et2: EditText
    lateinit var et3: EditText
    lateinit var updateButton: Button
    lateinit var deleteButton: Button
    lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        Log.d("UPDATE", "inside update activity")

        et1 = findViewById(R.id.etT1)
        et2 = findViewById(R.id.etT2)
        et3 = findViewById(R.id.etT3)
        etName = findViewById(R.id.etName)
        tvNum = findViewById(R.id.tvNum)
        updateButton = findViewById(R.id.buttonUpdate)
        cancelButton = findViewById(R.id.buttonCancel)
        deleteButton = findViewById(R.id.buttonDelete)

        Log.d("UPDATE", "Declarations 1")

        //score = intent.extras?.getInt("score")!!
        etName.setText(intent.extras?.getString("name"))
        et1.setText(intent.extras?.getString("t1"))
        et2.setText(intent.extras?.getString("t2"))
        et3.setText(intent.extras?.getString("t3"))
        tvNum.setText(intent.extras?.getInt("id").toString())

        Log.d("UPDATE", "Declarations 2")


        updateButton.setOnClickListener{
            var id = tvNum.text.toString().toInt()
            var person = Celeb.Details(etName.text.toString(),et1.text.toString(), et2.text.toString(),et3.text.toString(), id)
            Log.d("POST", "person is: $person")
            updatePerson(person,id)
            cancelAddition()
        }
        deleteButton.setOnClickListener {
            var id = tvNum.text.toString().toInt()
            deletePerson(id)
            Log.d("POST", "key is: ${id}")
            cancelAddition()
        }
        cancelButton.setOnClickListener { cancelAddition() }

    }

    fun updatePerson(person: Celeb.Details, key:Int) {
        val progressDialog = ProgressDialog(this)
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                progressDialog.setMessage("Please wait")
                progressDialog.show()
            }
            Log.d("POST", "fetch data")
            async {
                val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
                //val call: Call<Celeb.Details> = apiInterface!!.updatePerson(person.key!!,person)
                //val response: Response<Celeb.Details>
                try {
                    apiInterface!!.updatePerson(key,person).execute()
                    Log.d("POST", "fetch successful")
                }
                catch (e: Exception){ Log.d("POST", "ISSUE: $e") }
            }.await()
            withContext(Dispatchers.Main){
                progressDialog.dismiss() }
        }
    }

    fun deletePerson(key : Int) {
        val progressDialog = ProgressDialog(this)
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                progressDialog.setMessage("Please wait")
                progressDialog.show()
            }
            Log.d("POST", "fetch data")
            async {
                val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
                val call = apiInterface!!.deletePerson(key)
                try {
                    val response = call.execute()
                    Log.d("POST", "fetch successful")
                }
                catch (e: Exception){ Log.d("POST", "ISSUE: $e") }
            }.await()
            withContext(Dispatchers.Main){
                progressDialog.dismiss() }
        }
    }

    fun cancelAddition() {
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}