package com.example.listofemployees

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listofemployees.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val adapter = AdapterOfEmployees(this)
    private var editLauncher: ActivityResultLauncher<Intent>? = null

//    private fun jsonDataFromAssert(filename: String) : String {
//        var json = ""
//        try {
//            val inputStream = assets.open(filename)
//            val sizeOfFile = inputStream.available()
//            val bufferData = ByteArray(sizeOfFile)
//            inputStream.read(bufferData)
//            inputStream.close()
//            json = String(bufferData)
//        } catch (e : IOException){
//            e.printStackTrace()
//        }
//        return json
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
//        try{
//            val jsonObject = JSONObject(jsonDataFromAssert("employees.json"))
//            val jsonArray = jsonObject.getJSONArray("employees")
//            for (i in 0..jsonArray.length()){
//                val employeeData = jsonArray.getJSONObject(i)
//                val name = employeeData.getString("name")
//                val post = employeeData.getString("post")
//                val description = employeeData.getString("description")
//                adapter.addEmployee(Employee(name,post,description))
//            }
//        }catch (e : JSONException){
//            e.printStackTrace()
//        }
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK){
                adapter.addEmployee(it.data?.getSerializableExtra("employee") as Employee)
            }
        }
    }


    private fun init(){
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(this@MainActivity)
            rcView.adapter = adapter

            addButton.setOnClickListener{
                editLauncher?.launch(Intent(this@MainActivity, EditActivity::class.java))
            }

            val bandoleros = MediaPlayer.create(this@MainActivity, R.raw.bandoleros)
            musicButton.setOnClickListener{
                if (bandoleros.isPlaying){
                    bandoleros.pause()
                    musicButton.setImageResource(R.drawable.ic_play)
                } else {
                    bandoleros.start()
                    musicButton.setImageResource(R.drawable.ic_pause)
                }
            }
        }
    }
}