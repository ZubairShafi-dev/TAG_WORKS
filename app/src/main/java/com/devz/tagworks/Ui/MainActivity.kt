package com.devz.tagworks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.devz.tagworks.Ui.AdminFragment
import com.devz.tagworks.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.tvAdmin.setOnClickListener {
          loadAdminFragment()
        }
    }


    private fun loadAdminFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        val adminFragment = AdminFragment()
        transaction.addToBackStack(null)

        transaction.replace(R.id.fragmentContainer, adminFragment)
        transaction.commit()
    }
}