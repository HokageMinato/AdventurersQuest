package com.example.kotlean


class StoryState(imageId: Int,message:String, options: Array<String>,isLast:Boolean = false)
{
    public var imageId : Int = imageId;
    public val message : String = message;
    public val optionButtonTexts : Array<String> = options;
    public var nextStates : Array<String>? = null;
    public val isEndState : Boolean = isLast;
    public var rewardGrants : Array<String>? = null;
}


class Story(val gameScreen : GameScree)
{

    private val uninitializedState = StoryState(R.drawable.ic_launcher_foreground,"Uninitialized",arrayOf("Uninitialized","Uninitialized","Uninitialized","Uninitialized"));

    private val SwordRewardId :String = "SWROD";
    private val PowerBoostId :String = "PWRBOOST";

    private var presentState = uninitializedState;
    private var swordAcquired : Boolean = false;
    private var powerBoostAcquired : Boolean = false;



    private val statesMap = mutableMapOf<String,StoryState>();

    public fun InitializeEngine()
    {

       statesMap.put("initialState",StoryState(R.drawable.fadinglane,"You are on the road, There is a wooden sign nearby \n What will you do ?",
            arrayOf("Go North","Go East", "Go West", "Read the Sign")));


       statesMap.put("gameEndState",StoryState(R.drawable.ic_launcher_foreground,"", arrayOf(""),true));


       statesMap.put("plantDeathState",StoryState(R.drawable.grave,
            "You died, You adventure ends here",
            arrayOf("Return to title screen")));


       statesMap.put("gargoyleDeathState" , StoryState(R.drawable.grave,
                                "The gargoyle killed you, You adventure ends here",
                                arrayOf("Return to title screen")));


        statesMap.put("readSignState",StoryState(R.drawable.woodensign,
            "The sign says \n MONSTER AHEAD !",
            arrayOf("Back !")))


        statesMap.put("swordFoundState",StoryState(R.drawable.sword,
                                                "You found a master sword !"
                                            ,arrayOf("Go back")));


        statesMap["swordFoundState"]?.rewardGrants = arrayOf(SwordRewardId);



        statesMap.put("warpPipeState",StoryState(R.drawable.warppipe,
                                             "You saw a gigantic warp pipe",
                                              arrayOf("Look into it","Turn back")));


        statesMap.put("carnivorousPlantState",StoryState(R.drawable.carnivorousplant,
                    "Carnivorous Plant is inside!! \n alas you have been eaten by it !",
                            arrayOf(">")));



        statesMap.put("gargoyleState" , StoryState(R.drawable.gargoyle,
                            "You encounter the Gargoyle !!",
                                arrayOf("Attack","Try Run for life")));


        statesMap.put("failedAttackState",StoryState(R.drawable.playerdeath,
            "The gargoyle was too strong for you, you lose",
                                    arrayOf(">")));



        statesMap.put("treasureState" , StoryState(R.drawable.treasurechest,
                                   "You defeated the gargoyle, The treasure he hoarded is yours ! \n THE END",
                                        arrayOf(">")))


        statesMap.get("initialState")?.nextStates = arrayOf("gargoyleState","swordFoundState","warpPipeState","readSignState");
        statesMap.get("gargoyleState")?.nextStates = arrayOf("failedAttackState","gargoyleDeathState");
        statesMap.get("failedAttackState")?.nextStates = arrayOf("gameEndState");
        statesMap.get("gargoyleDeathState")?.nextStates = arrayOf("gameEndState");
        statesMap.get("swordFoundState")?.nextStates = arrayOf("initialState");



        statesMap.get("warpPipeState")?.nextStates = arrayOf("carnivorousPlantState","initialState");
        statesMap.get("carnivorousPlantState")?.nextStates = arrayOf("plantDeathState");
        statesMap.get("plantDeathState")?.nextStates = arrayOf("gameEndState");
        statesMap.get("readSignState")?.nextStates = arrayOf("initialState");
        statesMap.get("treasureState")?.nextStates = arrayOf("gameEndState")





        presentState = statesMap.get("initialState")!!;
        UpdateView();
    }

    public fun OnOptionSelected(selectedOption: Int)
    {
        UpdateState(selectedOption);
        UpdateView();
    }

    //region PRIVATE FUNCTIONS

    private fun UpdateState(selectedOption: Int)
    {
        val nextStates = presentState.nextStates;
        if(nextStates != null)
        {
           presentState = statesMap.get(nextStates[selectedOption])!!;
        }

        CheckForPickups();
    }

    private fun CheckForPickups()
    {
        val rewardGrants = presentState.rewardGrants;

        if(rewardGrants == null)
            return;

        if(!swordAcquired && rewardGrants.contains(SwordRewardId))
        {
            UpdateCarnivorousPlantStates()
            UpdateInitialState(arrayOf("Go North","Go West", "Read the Sign"),
                               arrayOf("gargoyleState","warpPipeState","readSignState"));
            swordAcquired = true;
        }

        if(!powerBoostAcquired && rewardGrants.contains(PowerBoostId))
        {

            UpdateGargoyleStates();
            UpdateInitialState(arrayOf("Go North", "Read the Sign"),
                arrayOf("gargoyleState","readSignState"));
            powerBoostAcquired = true;
        }

    }

    private fun UpdateInitialState(optionTexts:Array<String>, states:Array<String>)
    {
        statesMap["initialState"] = StoryState(R.drawable.fadinglane,"You are on the road, There is a wooden sign nearby \n What will you do ?",
            optionTexts);

        statesMap["initialState"]?.nextStates = states;
    }

    private fun UpdateGargoyleStates()
    {
        statesMap["gargoyleState"] = StoryState(R.drawable.gargoyle,
            "You encounter the Gargoyle !!",
            arrayOf("Attack.."))

        statesMap["gargoyleState"]?.nextStates = arrayOf("treasureState")

    }

    private fun UpdateCarnivorousPlantStates()
    {
        statesMap["carnivorousPlantState"] = StoryState(
            R.drawable.carnivorousplant,
            "You defeated carnivorous plant with your sword, \n you boost up your power by 30%",
            arrayOf("Go back")
        )

        statesMap["carnivorousPlantState"]?.rewardGrants = arrayOf(PowerBoostId);
        statesMap["carnivorousPlantState"]?.nextStates = arrayOf("initialState");
    }

    private fun UpdateView()
    {
       gameScreen.UpdateView(presentState);
    }

    //endregion


}