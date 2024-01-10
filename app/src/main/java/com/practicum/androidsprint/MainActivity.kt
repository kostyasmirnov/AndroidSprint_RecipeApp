package com.practicum.androidsprint

import android.os.Bundle
import android.view.View
import android.widget.Button
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

        val btnCategories: Button = findViewById(R.id.btnCategories)
        val btnFavorites: Button = findViewById(R.id.btnFavorites)

        btnCategories.setOnClickListener {
            supportFragmentManager.commit {
                replace(R.id.mainContainer, CategoriesListFragment())
            }
        }
        btnFavorites.setOnClickListener {
            supportFragmentManager.commit {
                replace(R.id.mainContainer, FavoritesFragment())
            }
        }
    }
}