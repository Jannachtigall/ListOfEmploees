package com.example.listofemployees

import java.io.Serializable

data class Employee(
    val name: String,
    val post: String
): Serializable
