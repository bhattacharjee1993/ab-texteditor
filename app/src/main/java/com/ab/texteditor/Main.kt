package com.ab.texteditor

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView

/**
 * Created by: anirban on 16/11/17.
 */
class Main {
    interface View: MvpLceView<Any>{

    }

    interface Presenter:MvpPresenter<View>{
        fun initialSetup()

    }
}