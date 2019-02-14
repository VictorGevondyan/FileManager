package com.example.filemanager.util

import android.support.v4.content.FileProvider

/**
 * Created by victor on 2/14/19.
 *
 * From Android 7.0 your app is not allowed to share a ‘file:///’ URI with an Intent,
 * we would require that to open files when when the user taps on them.
 * We can fix this behaviour by proving a FileProvider and then getting
 * a shareable Uri for our ‘file:///’ path.
 */
class GenericFileProvider : FileProvider()