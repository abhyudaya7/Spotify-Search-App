package com.example.spotifysearchapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.example.spotifysearchapp.ui.viewmodel.MainViewModel
import com.example.spotifysearchapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        viewModel
    }
}