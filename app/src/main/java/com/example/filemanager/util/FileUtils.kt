package com.example.filemanager.util

import android.content.Context
import android.content.Intent
import android.support.v4.content.FileProvider
import com.example.filemanager.model.FileModel
import java.io.File

/**
 * Created by victor on 2/14/19.
 */

class FileUtils {

    companion object {

        fun getFilesFromPath(path: String, onlyFolders: Boolean = false): List<File> {
            val file = File(path)
            return file.listFiles()
                .filter { !onlyFolders || it.isDirectory }
                .toList()
        }

        fun getFileModelsFromFiles(files: List<File>): List<FileModel> {
            return files.map {
                FileModel(
                    it.path,
                    FileType.getFileType(it),
                    it.name,
                    convertFileSizeToMB(it.length()),
                    it.extension,
                    it.listFiles()?.size
                            ?: 0
                )
            }
        }

        fun convertFileSizeToMB(sizeInBytes: Long): Double {
            val sizeInMB = (sizeInBytes.toDouble()) / (1024 * 1024)
            return roundToDecimals(sizeInMB, 1)
        }

        fun roundToDecimals(number: Double, numDecimalPlaces: Int): Double {
            val factor = Math.pow(10.0, numDecimalPlaces.toDouble())
            return Math.round(number * factor) / factor
        }

        fun Context.launchFileIntent(fileModel: FileModel) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = FileProvider.getUriForFile(this, packageName, File(fileModel.path))
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            startActivity(Intent.createChooser(intent, "Select Application"))
        }

    }

}