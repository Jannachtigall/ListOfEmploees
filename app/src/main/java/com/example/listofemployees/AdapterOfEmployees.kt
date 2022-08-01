package com.example.listofemployees

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listofemployees.databinding.EmployeeItemBinding


class AdapterOfEmployees():
    RecyclerView.Adapter<AdapterOfEmployees.EmployeesHolder>(){
    val employeeList = ArrayList<Employee>()
    class EmployeesHolder(item: View): RecyclerView.ViewHolder(item){
        val binding = EmployeeItemBinding.bind(item)
        fun bind(employee: Employee) = with(binding){
            nameTextView.text = employee.name
            postTextView.text = employee.post
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.employee_item, parent, false)
        return EmployeesHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeesHolder, position: Int) {
        holder.bind(employeeList[position])
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addEmployee(employee: Employee){
        employeeList.add(employee)
        notifyDataSetChanged()
    }

    fun removeEmployee(){

    }

}