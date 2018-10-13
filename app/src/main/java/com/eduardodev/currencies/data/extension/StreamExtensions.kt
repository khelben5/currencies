package com.eduardodev.currencies.data.extension

import java.io.InputStream


fun InputStream.readContent(): String = bufferedReader().useLines {
    it.fold(StringBuilder()) { acc, line -> acc.append(line) }.toString()
}