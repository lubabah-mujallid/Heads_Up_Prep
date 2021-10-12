package com.example.headsupprep

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostActivity : AppCompatActivity() {
    lateinit var etName: EditText
    lateinit var et1: EditText
    lateinit var et2: EditText
    lateinit var et3: EditText
    lateinit var addButton: Button
    lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        etName = findViewById(R.id.etName)
        et1 = findViewById(R.id.etAddT1)
        et2 = findViewById(R.id.etAddT2)
        et3 = findViewById(R.id.etAddT3)
        addButton = findViewById(R.id.buttonSave)
        cancelButton = findViewById(R.id.buttonCancel)

        addButton.setOnClickListener {
            var person = Celeb.Details(etName.text.toString(),et1.text.toString(),et2.text.toString(),et3.text.toString(),1)
            Log.d("POST", "person is: $person")
            newPerson(person)
            etName.setText("")
            et1.setText("")
            et2.setText("")
            et3.setText("")

        }
        cancelButton.setOnClickListener { cancelAddition() }
    }

    fun newPerson(person: Celeb.Details) {
        val progressDialog = ProgressDialog(this)
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                progressDialog.setMessage("Please wait")
                progressDialog.show()
            }
            Log.d("POST", "fetch data")
            async {
                val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
                val call: Call<List<Celeb.Details>> = apiInterface!!.addPerson(person)
                val response: Response<List<Celeb.Details>>
                try {
                    response = call.execute()
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