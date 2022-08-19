package com.example.listofemployees

class surnameComparator : Comparator<Employee>{
    override fun compare(p0: Employee?, p1: Employee?): Int {
        var result = p0!!.name[0].code - p1!!.name[0].code
        if (result == 0) result = p0.name[1].code - p1.name[1].code
        if (result == 0) result = p0.name[2].code - p1.name[2].code
        return result
        //Извините за самую ленивую реализацию, которую только можно придумать,
        // мне просто нетерпелось сдать
    }
}