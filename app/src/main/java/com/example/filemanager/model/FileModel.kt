package com.example.filemanager.model

import com.example.filemanager.util.FileType
import java.io.File

data class FileModel(
    val path: String,
    val fileType: FileType,
    val name: String = "",
    val sizeInMB: Double,
    val extension: String = "",
    val subFiles: Int = 0
) : Comparable<FileModel> {

    private val file = File(path)

    override fun compareTo(other: FileModel): Int {
        return if ((fileType == FileType.FOLDER) && (other.fileType != FileType.FOLDER)) {
            -1
        } else if ((fileType != FileType.FOLDER) && (other.fileType == FileType.FOLDER)) {
            1
        } else {
            var result: Int = name.toLowerCase().compareTo(other.name.toLowerCase())
            result
        }
    }

    fun getLastModified(): Long {
        return file.lastModified()
    }

    fun getDetails(): String {

        return if (fileType == FileType.FOLDER) {
            getChildCount()
        } else {
            getFileSize()
        }
    }

    fun getChildCount(): String {
        return (subFiles.toString() + " Items")
    }

    fun getFileSize(): String {
        return (sizeInMB.toString() + " MB")
    }

}