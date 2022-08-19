package com.example.listofemployees

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.listofemployees.databinding.ActivityEditBinding
import java.util.*

class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding
    lateinit var setListener : DatePickerDialog.OnDateSetListener

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Новенький"
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        binding.editDate.isFocusable = false
        binding.editDate.keyListener = null

        setListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                val strmonth: String
                if (month+1 < 10) strmonth = "0${month+1}"
                else strmonth = "${month+1}"
                val date = "${dayOfMonth}.${strmonth}.${year}"
                binding.editDate.setText(date)
            }
        }
        binding.editDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    val strmonth: String
                    if (month+1 < 10) strmonth = "0${month+1}"
                    else strmonth = "${month+1}"
                    val date = "${dayOfMonth}.${strmonth}.${year}"
                    binding.editDate.setText(date)
                }
            },year,month,day)
            datePickerDialog.show()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initButtons() = with(binding){
        bDone.setOnClickListener{
            val emp = Employee(
                editName.text.toString(),
                editPost.text.toString(),
                "",
                editDate.text.toString(),
                editDescription.text.toString())
            val editIntent = Intent().apply {
                putExtra("employee", emp)
            }
            setResult(RESULT_OK, editIntent)
            finish()
        }
        avatar.setOnClickListener{
            Toast.makeText(this@EditActivity, "COMING SOON", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return true
    }
}