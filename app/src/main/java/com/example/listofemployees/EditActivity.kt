package com.example.listofemployees

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.listofemployees.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()
    }

    private fun initButtons() = with(binding){
        bDone.setOnClickListener{
            val emp = Employee(
                editName.text.toString(),
                editPost.text.toString(),
                editDescription.text.toString())
            val editIntent = Intent().apply {
                putExtra("employee", emp)
            }
            setResult(RESULT_OK, editIntent)
            finish()
        }
        cButton.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}