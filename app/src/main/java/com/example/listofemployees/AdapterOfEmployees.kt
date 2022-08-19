package com.example.listofemployees

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.listofemployees.databinding.EmployeeItemBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.lang.RuntimeException
import java.util.*
import kotlin.Comparator


class AdapterOfEmployees(val context: Context):
    RecyclerView.Adapter<AdapterOfEmployees.EmployeesHolder>(){
    private val path = "${context.cacheDir}/data.json"
    val employeeList = getEmployeesList()

    class EmployeesHolder(item: View): RecyclerView.ViewHolder(item){
        val binding = EmployeeItemBinding.bind(item)

        @SuppressLint("CheckResult")
        fun bind(employee: Employee, onRemove : (Int) -> Unit) = with(binding){
            nameTextView.text = employee.name
            postTextView.text = employee.post
            if (employee.photo.isNotBlank()){
                Glide.with(itemView.context)
                    .load(employee.photo)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .circleCrop()
                    .placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .into(avatarView)
            } else {
                avatarView.setImageResource(R.drawable.ic_person)
            }
            object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    onRemove.invoke(position)
                }

            }
//            removeImageView.setOnClickListener {
//                onRemove.invoke(position)
//            }
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
        addEmp(employee)
        employeeList.add(employee)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeEmployee(position : Int){
        deleteEmp(employeeList[position])
        employeeList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, employeeList.size)
    }

    fun getEmployeesList(): MutableList<Employee> {
        var result = "" // Переменная, куда записывается JSON-файл в текстовом виде
        val isFileExists = File(path).exists() // Проверка, существует ли нужный файл в cacheDir
        val inputStream = if (isFileExists) { // Если файл существует, то в переменную запишется InputStream уже файла из cacheDir
            File(path).inputStream()
        } else {
            context.assets.open("employees.json") // Если файла не существует, то в переменную запишется InputStream из файла в assets
        }
        try {
            result = inputStream.bufferedReader().use { it.readText() } // Считывание данных из входного потока (InputStream)
            if (!isFileExists) writeDataToCacheFile(result) // Если файла не существует, то сразу запишутся считанные данные из assets файла
        } catch (e: Exception) {
            e.printStackTrace() // если упали в ошибку, то они напечатаются в логах
        } finally {
            inputStream.close() // закрытие входного потока
        }

        val employees = Gson().fromJson(result, Employees::class.java)
        return employees.employees // парсинг массива по указанному типу строки
    }

    // Метод, который перевод массив в json-строку и проваливается в сохранение новых данных в файл
    fun saveList(list: MutableList<Employee>) {
        val employees = Employees(list)
        val result = Gson().toJson(employees)

        writeDataToCacheFile(result) // вызов метода, для записи в файл
//        writeDataToCacheFile(Gson().toJson(list)) // Более короткая реализация
    }

    // Метод для сохранения json-строки в файл
    private fun writeDataToCacheFile(result: String) {
        if (!File(path).exists()) {
            File(path).createNewFile() // еще раз проверяем, существует ли файл, если нет, то создаем
        }
        val outputStream = File(path).outputStream() // открываем выходной поток
        try {
            outputStream.bufferedWriter().use { it.write(result) } // запись данных по выходному потоку
        } catch (e: Exception) {
            e.printStackTrace() // если упали в ошибку, то они напечатаются в логах
        } finally {
            outputStream.close()// закрытие выходного потока
        }
    }

    // Метод для добавления нового студента
    fun addEmp(emp: Employee) {
        val employeesList = getEmployeesList()
        employeesList.add(emp)
        saveList(employeesList)
    }

    // Метод для удаления студента
    fun deleteEmp(emp: Employee) {
        val employeesList = getEmployeesList()
        employeesList.remove(emp)
        saveList(employeesList)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sorting(comparator: Comparator<Employee>){
        try {
            val employeesList = getEmployeesList()
            Collections.sort(employeesList, comparator)
            Collections.sort(employeeList, comparator)
            saveList(employeesList)
            notifyDataSetChanged()
        } catch (e : RuntimeException){
            Toast.makeText(context, "Не хватает данных", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}