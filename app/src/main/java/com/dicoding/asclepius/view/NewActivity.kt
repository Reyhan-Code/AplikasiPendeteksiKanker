package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.R
import com.dicoding.asclepius.adapter.NewAdapter
import com.dicoding.asclepius.databinding.ActivityNewBinding
import com.dicoding.asclepius.viewmodel.NewViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewBinding
    private lateinit var newAdapter: NewAdapter
    private lateinit var newViewModel: NewViewModel
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var newsRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView = findViewById(R.id.menu)
        newsRecyclerView = findViewById(R.id.rvNewList)
        bottomNavigationView.selectedItemId = R.id.nav_new
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }

                R.id.nav_new -> {
                    true
                }

                else -> false
            }

        }
        initRecyclerView()
        newViewModel = ViewModelProvider(this)[NewViewModel::class.java]
        newViewModel.fetchHealthNews()
        newViewModel.newsList.observe(this, Observer { newsList ->
            newAdapter.submitList(newsList)
        })

        with(newViewModel) {
            isLoading.observe(this@NewActivity) { showLoading(it) }
        }
    }

    private fun initRecyclerView() {
        newAdapter = NewAdapter()
        binding.rvNewList.apply {
            adapter = newAdapter
            layoutManager = LinearLayoutManager(this@NewActivity)
        }
    }


    fun openNewsUrl(view: View) {
        val url = view.getTag(R.id.tvLink) as? String
        url?.let {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressIndicator.visibility = View.VISIBLE
        } else {
            binding.progressIndicator.visibility = View.GONE
        }
    }
}


