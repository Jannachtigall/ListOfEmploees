package com.example.listofemployees

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.listofemployees.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    lateinit var binding: ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Дополнительная информация"
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val obj = intent.getSerializableExtra("family") as Employee
        with(binding){
            nameView.text = obj.name
            postView.text = obj.post
            if (obj.photo.isNotBlank()){
                Glide.with(photoView.context)
                    .load(obj.photo)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .circleCrop()
                    .placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .into(photoView)
            } else {
                photoView.setImageResource(R.drawable.ic_person)
            }
            descriptionView.text = obj.description
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return true
    }
}