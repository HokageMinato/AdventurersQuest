package com.example.kotlean


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

class GameScree : AppCompatActivity()
{

    val TAG = "GAME_SCREEN";
    var gameImageView: ImageView? = null;
    var gameTextView: TextView? = null;
    val story: Story = Story(this);
    var optionButtonsList: MutableList<Button> = mutableListOf<Button>();


    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_scree)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        Initialize();
    }

    private fun SetButtons(optionTexts:Array<String>?)
    {
        for(button in optionButtonsList)
        {
            button.isVisible = false;
        }

        optionTexts?.forEachIndexed { i:Int, text:String ->

            val button = optionButtonsList[i];
            button.text = optionTexts[i];
            button.isVisible = true;

        };
    }

    private fun SetTextMessage(message:String?)
    {
        gameTextView?.text = message;
    }

    private fun SetImage(imageId:Int)
    {
        gameImageView?.setImageResource(imageId);
    }
    //endregion

    //region PRIVATE_FUNCTIONS

    private fun Initialize()
    {

        gameTextView = findViewById(R.id.gameMsg_textView);
        gameImageView = findViewById(R.id.gameImageView);

        val button1 = findViewById<Button>(R.id.option_button_1);
        val button2 = findViewById<Button>(R.id.option_button_2);
        val button3 = findViewById<Button>(R.id.option_button_3);
        val button4 = findViewById<Button>(R.id.option_button_4);


        button1.setOnClickListener{
            story.OnOptionSelected(0);
        }

        button2.setOnClickListener{
            story.OnOptionSelected(1);
        }

        button3.setOnClickListener{
            story.OnOptionSelected(2);
        }

        button4.setOnClickListener{
            story.OnOptionSelected(3);
        }

        optionButtonsList.add(button1);
        optionButtonsList.add(button2);
        optionButtonsList.add(button3);
        optionButtonsList.add(button4);

        story.InitializeEngine();
    }

    //endregion

    //region PUBLIC_METHODS
    public fun UpdateView(storyState:StoryState)
    {
        SetImage(storyState.imageId);
        SetTextMessage(storyState.message);
        SetButtons(storyState.optionButtonTexts);
        if(storyState.isEndState)
        {
            val intent = Intent(this,TitleScreen::class.java);
            startActivity(intent);
            gameTextView = null;
            optionButtonsList.clear();
            finish();
        }
    }
    //endregion
}