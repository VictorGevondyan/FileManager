package com.example.filemanager.util

import java.io.File

/**
 * Created by victor on 2/14/19.
 */
enum class FileType {

    FILE,
    FOLDER;

    companion object {
        fun getFileType(file: File) = when (file.isDirectory) {
            true -> FOLDER
            false -> FILE
        }
    }

}