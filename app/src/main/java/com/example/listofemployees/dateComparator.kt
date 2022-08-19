package com.example.listofemployees

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import kotlin.Comparator

class dateComparator : Comparator<Employee> {
    @SuppressLint("SimpleDateFormat")
    override fun compare(p0: Employee, p1: Employee): Int {
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        return (formatter.parse(p0.birthday)!!.time - formatter.parse(p1.birthday)!!.time).toInt()
    }
}