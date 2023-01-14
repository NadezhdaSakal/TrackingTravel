package com.maximvs.trackingtravel.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.maximvs.trackingtravel.databinding.ActivityStartBinding
import com.maximvs.trackingtravel.view.MainActivity


class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBtn.setOnClickListener {
            val intent = Intent(this@StartActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


}
