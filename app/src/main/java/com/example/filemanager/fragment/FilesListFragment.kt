package com.example.filemanager.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.filemanager.R
import com.example.filemanager.adapter.FilesRecyclerAdapter
import com.example.filemanager.model.FileModel
import com.example.filemanager.util.FileUtils
import kotlinx.android.synthetic.main.fragment_files_list.*
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.HORIZONTAL


/**
 * Created by victor on 2/14/19.
 */
class FilesListFragment : Fragment() {

    private lateinit var filesAdapter: FilesRecyclerAdapter
    private lateinit var path: String
    private lateinit var onItemClickListener: OnItemClickListener

    companion object {
        private const val ARG_PATH: String = "com.example.filemanager.fileslist.path"
        fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var path: String = ""

        fun build(): FilesListFragment {
            val fragment = FilesListFragment()
            val args = Bundle()
            args.putString(ARG_PATH, path)
            fragment.arguments = args;
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_files_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filePath = arguments?.getString(ARG_PATH)
        if (filePath == null) {
            Toast.makeText(context, "Path should not be null!", Toast.LENGTH_SHORT).show()
            return
        }
        path = filePath

        initViews(context)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            onItemClickListener = context as OnItemClickListener
        } catch (e: Exception) {
            throw Exception("${context} should implement FilesListFragment.OnItemCLickListener")
        }
    }

    private fun initViews(context: Context?) {

        filesRecyclerView.layoutManager = LinearLayoutManager(context)
        val itemDecor = DividerItemDecoration(context, HORIZONTAL)
        filesRecyclerView.addItemDecoration(itemDecor)
        filesAdapter = FilesRecyclerAdapter(context)
        filesRecyclerView.adapter = filesAdapter

        filesAdapter.onItemClickListener = {
            onItemClickListener.onClick(it)
        }

        filesAdapter.onItemLongClickListener = {
            onItemClickListener.onLongClick(it)
        }

        updateData()
    }

    private fun updateData() {
        val files = FileUtils.getFileModelsFromFiles(FileUtils.getFilesFromPath(path))
        val filesArrayList = ArrayList(files)

        if (files.isEmpty()) {
            emptyFolderLayout.visibility = View.VISIBLE
        } else {
            emptyFolderLayout.visibility = View.INVISIBLE
        }

        filesAdapter.updateData(filesArrayList)
    }

    interface OnItemClickListener {

        fun onClick(fileModel: FileModel)

        fun onLongClick(fileModel: FileModel)

    }

}