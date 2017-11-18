package com.ab.texteditor

import android.app.Application
import android.util.Log
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import rx.Observable
import rx.schedulers.Schedulers
import java.io.File


/**
 * Created by: anirban on 18/11/17.
 */
object RxNetworkHelper {
    var credentialsProvider: CognitoCachingCredentialsProvider? = null


    init {

    }

    fun uploadFile(application: Application, filePath: String) {
        credentialsProvider = credentialsProvider ?: CognitoCachingCredentialsProvider(
                application, /* get the context for the application */
                "ap-south-1:b7e4b37c-38b7-441f-9fbb-0a37e5cae43a", /* Identity Pool ID */
                Regions.AP_SOUTH_1           /* Region for your identity pool--US_EAST_1 or EU_WEST_1*/
        )
        val s3 = AmazonS3Client(credentialsProvider)
        val transferUtility = TransferUtility(s3, application.applicationContext)
        val file = File(filePath)

        Observable.just(transferUtility.upload(
                "textediting/images",     /* The bucket to upload to */
                file.name,    /* The key for the uploaded object */
                File(filePath)        /* The file where the data to upload exists */
        )).subscribeOn(Schedulers.io())
                .subscribe({
                    it.refresh()
                }, {
                    Log.e("TAG", "error: " + it.message)
                })

    }

    fun uploadText(){

    }

}