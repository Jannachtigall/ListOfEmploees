package com.example.listofemployees

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.listofemployees.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    lateinit var binding: ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val obj = intent.getSerializableExtra("family") as Employee
        binding.nameView.text = obj.name
        binding.postView.text = obj.post
        binding.descriptionView.text = obj.description
    }
}