package com.example.listofemployees

import java.io.Serializable
import java.util.*

data class Employee(
    val name: String,
    val post: String,
    val photo: String,
    val birthday: String,
    val description: String?
): Serializable

data class Employees(val employees: MutableList<Employee>)
