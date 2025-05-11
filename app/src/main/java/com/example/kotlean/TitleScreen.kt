package com.example.kotlean

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lantern.plugins.PluginManager

class TitleScreen : AppCompatActivity() {

    private val titleActivityTag:String = "TITLE_DBG_TAG";

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_title_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        RegisterStartButton_OnClick()
        InitializePluginManager();
        PluginManager.LogEvent("Plugin_Manager_initialized_successfully");
        PluginManager.LogEvent("MainActivity_Started");
    }

    private fun RegisterStartButton_OnClick()
    {
        findViewById<Button>(R.id.game_start_button)
            .setOnClickListener{
                val intent = Intent(this, GameScree::class.java);
                PluginManager.LogEvent("Play_Button_Clicked");
                startActivity(intent);
                finish();
            }
    }


    private fun InitializePluginManager()
    {
        Log.d(titleActivityTag,"Plugin Initialize Start")
        PluginManager.initializePlugins(applicationContext,false);
        Log.d(titleActivityTag,"Plugin Initialize End")

    }
}