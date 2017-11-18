package com.ab.texteditor.edit

import com.ab.texteditor.model.ModelBase
import com.ab.texteditor.model.TYPE_TEXT_MODEL
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter

/**
 * Created by: anirban on 16/11/17.
 */
class EditingPresenter : MvpBasePresenter<Editing.View>(), Editing.Presenter {
    override fun initialSetup() {
        view?.showContent()
        view?.setData(ModelBase().apply {
            position = 0
            type = TYPE_TEXT_MODEL
        })

    }

}