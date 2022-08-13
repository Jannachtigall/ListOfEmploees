package com.example.listofemployees

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listofemployees.databinding.EmployeeItemBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File


class AdapterOfEmployees(val context: Context):
    RecyclerView.Adapter<AdapterOfEmployees.EmployeesHolder>(){
    val employeeList = ArrayList<Employee>()//getEmployeesList()
    private val path = "${context.cacheDir}/data.json"

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

        val listStudentType = object: TypeToken<List<Employee>>() {}.type // указание, какого типа данные ждать из считанной строки (переменная result)
        return Gson().fromJson(result, listStudentType) // парсинг массива по указанному типу строки
    }

    // Метод, который перевод массив в json-строку и проваливается в сохранение новых данных в файл
    fun saveList(list: List<Employee>) {
        val result = Gson().toJson(list) // получение данных из массива в виде json-строки
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
        val studentList = getEmployeesList()
        studentList.add(emp)
        saveList(studentList)
    }

    // Метод для удаления студента
    fun deleteEmp(emp: Employee) {
        val studentList = getEmployeesList()
        studentList.remove(emp)
        saveList(studentList)
    }

}