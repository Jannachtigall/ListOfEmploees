package com.example.listofemployees

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listofemployees.databinding.EmployeeItemBinding
import org.json.JSONObject
import java.io.IOException


class AdapterOfEmployees(val context: Context):
    RecyclerView.Adapter<AdapterOfEmployees.EmployeesHolder>(){
    val employeeList = ArrayList<Employee>()

    class EmployeesHolder(item: View): RecyclerView.ViewHolder(item){
        val binding = EmployeeItemBinding.bind(item)

        fun bind(employee: Employee, onRemove : (Int) -> Unit) = with(binding){
            nameTextView.text = employee.name
            postTextView.text = employee.post
            removeImageView.setOnClickListener {
                onRemove.invoke(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.employee_item, parent, false)
        return EmployeesHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeesHolder, position: Int) {
        holder.bind(employeeList[position], this::removeEmployee)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, InfoActivity::class.java)
            intent.putExtra("family", employeeList[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addEmployee(employee: Employee){
        employeeList.add(employee)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeEmployee(position : Int){
        employeeList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, employeeList.size)
    }

}