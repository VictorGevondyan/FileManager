package com.example.filemanager.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.filemanager.R
import com.example.filemanager.model.FileModel
import com.example.filemanager.util.FileType
import kotlinx.android.synthetic.main.item_recycler_file.view.*
import java.text.SimpleDateFormat
import java.util.*

class FilesRecyclerAdapter(val context: Context?) :
    RecyclerView.Adapter<FilesRecyclerAdapter.ItemsViewHolder>() {

    private lateinit var folderDrawable: Drawable
    private lateinit var fileDrawable: Drawable
    var onItemClickListener: ((FileModel) -> Unit)? = null
    var onItemLongClickListener: ((FileModel) -> Unit)? = null

    var filesList = listOf<FileModel>()

    init {
        initDrawables()
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return filesList.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        return ItemsViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_recycler_file,
                parent,
                false
            )
        )
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: FilesRecyclerAdapter.ItemsViewHolder, position: Int) {

        val fileModel = filesList[position]

        if (fileModel.fileType == FileType.FOLDER) {
            holder.itemIcon.setImageDrawable(folderDrawable)
        } else {
            holder.itemIcon.setImageDrawable(fileDrawable)
        }

        holder.itemName.text = fileModel.name

        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(
            Date(fileModel.getLastModified())
        )

        holder.lastModified.text = context!!.getString(R.string.last_modified)
        holder.modifiedDate.text = simpleDateFormat

        holder.itemDetails.text = fileModel.getDetails()

    }

    fun updateData(filesList: ArrayList<FileModel>) {
        this.filesList = filesList
        filesList.sort()
        notifyDataSetChanged()
    }

    inner class ItemsViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

        // Holds the TextView that will add each animal to
        val itemIcon = view.item_icon
        val itemName = view.item_name
        val lastModified = view.last_modified
        val modifiedDate = view.modified_date
        val itemDetails = view.item_details

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            onItemClickListener?.invoke(filesList[adapterPosition])
        }

        override fun onLongClick(v: View?): Boolean {
            onItemLongClickListener?.invoke(filesList[adapterPosition])
            return true
        }

    }

    private fun initDrawables() {
        folderDrawable = context?.resources!!.getDrawable(R.drawable.ic_folder)
        fileDrawable = context?.resources.getDrawable(R.drawable.ic_file)
    }

}
