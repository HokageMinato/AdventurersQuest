package com.lantern.plugins
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics

object PluginManager {

    //region PRIVATE_VARS
    public var isLiveBuild:Boolean = false;
    private val TAG : String = "PluginManager";
    private lateinit var analytics: FirebaseAnalytics
    private lateinit var crashlytics: FirebaseCrashlytics

    //endregion

    //region PUBLIC_METHODS
    public fun initializePlugins(context: Context, isLiveBuild : Boolean)
    {
        this.isLiveBuild = isLiveBuild;
        initializeFirebase(context,isLiveBuild);
    }

    public fun LogEvent(eventName:String,parameters:Bundle? = null)
    {
        PDebug.Log("Logging event '${eventName}'")
        analytics.logEvent(eventName,parameters);
        PDebug.Log("Logging successful '${eventName}'")
    }

    //endregion


    //region PRIVATE_FUNCTIONS
    private fun initializeFirebase(context: Context , isLiveBuild : Boolean)
    {
        analytics = FirebaseAnalytics.getInstance(context);
        crashlytics = FirebaseCrashlytics.getInstance();
        crashlytics.isCrashlyticsCollectionEnabled = true;
    }
    //endregion



}