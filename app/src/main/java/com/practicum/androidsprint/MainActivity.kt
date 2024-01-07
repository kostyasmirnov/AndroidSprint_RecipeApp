package com.practicum.androidsprint

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.practicum.androidsprint.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val initialFragment = CategoriesListFragment()

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<CategoriesListFragment>(R.id.mainContainer)
        }
    }
}