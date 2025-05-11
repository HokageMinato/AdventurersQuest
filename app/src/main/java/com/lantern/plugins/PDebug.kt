package com.lantern.plugins
import android.util.Log;

object PDebug
{
    private val PLUGIN_DEBUG_TAG:String = "PL_DBG";

    public fun Log(message:String)
    {
        if(PluginManager.isLiveBuild)
            return;

        Log.d(PLUGIN_DEBUG_TAG,message);
    }

}