package com.example.scrollingreccyclerview.showdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.scrollingreccyclerview.R
import com.example.scrollingreccyclerview.databinding.ActivityShowUserDetailsBinding
import com.example.scrollingreccyclerview.models.Data

class ShowUserDetails : AppCompatActivity() {

    lateinit var showDetail : Data
    lateinit var binding: ActivityShowUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_show_user_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent!=null){
            showDetail = intent.extras?.getSerializable("detail") as Data
            binding.showName.text = showDetail.name
            binding.showEmail.text = showDetail.email
            Glide.with(this).load(showDetail.profile_picture).into(binding.showImage)
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}