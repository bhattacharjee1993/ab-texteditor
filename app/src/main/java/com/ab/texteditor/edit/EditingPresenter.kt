package com.ab.texteditor.edit

import android.content.Context
import com.ab.texteditor.RxNetworkHelper
import com.ab.texteditor.Utils
import com.ab.texteditor.constants.INTERNAL_STORAGE_PATH
import com.ab.texteditor.model.ModelBase
import com.ab.texteditor.model.TYPE_IMAGE_MODEL
import com.ab.texteditor.model.TYPE_TEXT_MODEL
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.vicpin.krealmextensions.queryAll
import com.vicpin.krealmextensions.saveAll
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File
import java.util.Collections.sort

/**
 * Created by: anirban on 16/11/17.
 */
class EditingPresenter : MvpBasePresenter<Editing.View>(), Editing.Presenter {
    override fun initialSetup(context: Context) {

        Observable.just(true)
                .subscribeOn(Schedulers.io())
                .map { ModelBase.hasData() }
                .concatMap { hasData ->
                    if (hasData)
                        return@concatMap Observable.just(ModelBase().queryAll().apply {
                            sort(this)
                        })

                    val s3 = AmazonS3Client(RxNetworkHelper.credentialsProvider)
                    val objectData = s3.getObject("textediting/deviceContent", Utils.getDeviceIdForPushNotification(context) + ".txt")
                    TransferUtility(s3, context).download("textediting/deviceContent", Utils.getDeviceIdForPushNotification(context) + ".txt",
                            File(INTERNAL_STORAGE_PATH + Utils.getDeviceIdForPushNotification(context) + ".txt"))
                    return@concatMap convertFileToDataList(context)
                }.observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.showContent()
                    view?.setData(ArrayList<ModelBase>().apply { addAll(it) })
                }, {
                    view?.showContent()
                    view?.setData(ArrayList<ModelBase>().apply { add(ModelBase().apply { type = TYPE_TEXT_MODEL }) })

                })

        /*view?.showContent()
        view?.setData(ModelBase().apply {
            position = 0
            type = TYPE_TEXT_MODEL
        })*/

    }

    private fun convertFileToDataList(context: Context): Observable<ArrayList<ModelBase>> {
        return Observable.just(File(INTERNAL_STORAGE_PATH + Utils.getDeviceIdForPushNotification(context) + ".txt"))
                .concatMap {
                    val text = it.readText()
                    return@concatMap Observable.just(text)
                }.concatMap {
            val list = arrayListOf<ModelBase>()
            val array = it.split("|||||")
            var model: ModelBase
            for (temp in array) {
                if (temp.startsWith("<<<<<")) {
                    model = ModelBase().apply {
                        type = TYPE_IMAGE_MODEL
                        position = list.size
                        fileName = temp.substring(5)
                    }
                } else {
                    model = ModelBase().apply {
                        type = TYPE_TEXT_MODEL
                        position = list.size
                        text = temp
                    }
                }
                list.add(model)
            }
            Observable.just(list.apply {
                sort()
                saveAll()
            })
        }
    }

    private fun convertFileToList() {


    }

}