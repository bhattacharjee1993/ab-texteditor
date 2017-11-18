package com.ab.texteditor

import com.ab.texteditor.model.Text
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter

/**
 * Created by: anirban on 16/11/17.
 */
class MainPresenter :MvpBasePresenter<Main.View>(), Main.Presenter{
    override fun initialSetup() {
        view?.showContent()
        view?.setData(Text().apply { this.position = 1 })
    }

}