package com.ab.texteditor

import android.app.Application
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.regions.Regions
import kotlin.properties.Delegates


/**
 * Created by: anirban on 18/11/17.
 */
open class App : Application(){
   open var credentialsProvider: CognitoCachingCredentialsProvider by Delegates.notNull()

    override fun onCreate() {
        super.onCreate()
        credentialsProvider = CognitoCachingCredentialsProvider(
                applicationContext, /* get the context for the application */
                "ap-south-1:f00a7cac-eb96-4354-b721-e700961e8d69", /* Identity Pool ID */
                Regions.AP_SOUTH_1           /* Region for your identity pool--US_EAST_1 or EU_WEST_1*/
        )

    }

}