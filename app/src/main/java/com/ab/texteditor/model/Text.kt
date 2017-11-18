package com.ab.texteditor.model

import io.realm.RealmObject

/**
 * Created by: anirban on 16/11/17.
 */
open class Text : RealmObject() {
    open var text: String? = null
    open var position: Int? = null
    open var isUploaded: Boolean = false
}