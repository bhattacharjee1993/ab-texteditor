package com.ab.texteditor.edit

import android.content.Context
import android.util.Log
import com.ab.texteditor.model.ModelBase
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.vicpin.krealmextensions.queryAll
import rx.Observable
import rx.schedulers.Schedulers
import java.util.Collections.sort

/**
 * Created by: anirban on 16/11/17.
 */
class EditingPresenter : MvpBasePresenter<Editing.View>(), Editing.Presenter {
    override fun initialSetup(context: Context) {

        Observable.just(true)
                .subscribeOn(Schedulers.io())
                .map { ModelBase.hasData() }
                .concatMap {
                    hasData ->
                    if(hasData)
                        return@concatMap Observable.just(ModelBase().queryAll().apply {
                            sort(this)
                        })

//                    val s3 = AmazonS3Client(RxNetworkHelper.credentialsProvider)
//                    val objectData = s3.getObject("textediting/deviceContent",Utils.getDeviceIdForPushNotification(context)+".txt")

                    return@concatMap Observable.just(arrayListOf<ModelBase>())
                }.subscribe({
                    view?.showContent()
                    view?.setData(ArrayList<ModelBase>().apply { addAll(it) })
        }, {
            Log.e("tag","error")

        })

        /*view?.showContent()
        view?.setData(ModelBase().apply {
            position = 0
            type = TYPE_TEXT_MODEL
        })*/

    }

}