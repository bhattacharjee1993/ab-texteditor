package com.ab.texteditor

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ab.texteditor.model.Image
import com.ab.texteditor.model.Text
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image.view.*
import kotlinx.android.synthetic.main.item_text.view.*
import java.io.File

/**
 * Created by: anirban on 16/11/17.
 */
const val TYPE_TEXT = 100
const val TYPE_IMAGE = 101

class MainAdapter(val items: ArrayList<Any>) : RecyclerView.Adapter<MainAdapter.BaseViewHolder>() {


    override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {
        holder?.bind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TYPE_IMAGE -> ImageViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_image, parent, false))
            else -> TextViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_text, parent, false), CustomTextWatcher())
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Text -> TYPE_TEXT
            else -> TYPE_IMAGE
        }
    }

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(position: Int)
    }

    inner class TextViewHolder(val view: View, private val customTextWatcher: CustomTextWatcher) : BaseViewHolder(view) {
        override fun bind(position: Int) {
            customTextWatcher.updatePosition = position
            view.text.addTextChangedListener(customTextWatcher)
            view.text.setText(((items[position] as Text).text.toString()), TextView.BufferType.EDITABLE)
        }
    }

    inner class ImageViewHolder(val view: View) : BaseViewHolder(view) {
        override fun bind(position: Int) {
            Picasso.with(view.context)
                    .load(File((items[position] as Image).imageUrl))
                    .into(view.image)
        }
    }

    inner class CustomTextWatcher : TextWatcher {

        var updatePosition: Int = 0


        override fun afterTextChanged(p0: Editable?) {
            (items[updatePosition] as Text).text = p0.toString()

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

    }

    fun add(image: Image) {
        val size = items.size
        items.apply {
            add(image.apply image@ { this@image.orderPosition = size })
            add(Text().apply text@ { this@text.position = size + 1 })
        }
        notifyDataSetChanged()
    }

    fun add(text: Text) {
        items.add(text)
        notifyDataSetChanged()
    }
}