package com.ab.texteditor.model

import io.realm.RealmObject

/**
 * Created by: anirban on 16/11/17.
 */
open class Image : RealmObject() {
    open var imageUrl: String? = null
        set(value) {
            field = value
            fileName = field?.substring(field?.lastIndexOf('/') ?: 0)
        }
    open var orderPosition: Int? = null
    open var isUploaded: Boolean = false
    open var fileName: String? = null
}