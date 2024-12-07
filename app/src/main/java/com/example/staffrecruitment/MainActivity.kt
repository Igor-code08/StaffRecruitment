package com.example.staffrecruitment

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editFirstName: EditText
    private lateinit var editLastName: EditText
    private lateinit var editAge: EditText
    private lateinit var spinnerPosition: Spinner
    private lateinit var buttonSave: Button
    private lateinit var listViewEmployees: ListView

    private val employees = mutableListOf<Person>()
    private lateinit var adapter: ArrayAdapter<String>
    private val positions = listOf("Инженер", "Конструктор", "Энергетик", "Менеджер", "Программист")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация элементов интерфейса
        editFirstName = findViewById(R.id.editFirstName)
        editLastName = findViewById(R.id.editLastName)
        editAge = findViewById(R.id.editAge)
        spinnerPosition = findViewById(R.id.spinnerPosition)
        buttonSave = findViewById(R.id.buttonSave)
        listViewEmployees = findViewById(R.id.listViewEmployees)

        // Настройка Spinner для должностей
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, positions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPosition.adapter = spinnerAdapter

        // Настройка ListView
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listViewEmployees.adapter = adapter

        // Обработка нажатия кнопки "Сохранить"
        buttonSave.setOnClickListener {
            val firstName = editFirstName.text.toString()
            val lastName = editLastName.text.toString()
            val age = editAge.text.toString().toIntOrNull()
            val position = spinnerPosition.selectedItem.toString()

            if (firstName.isNotBlank() && lastName.isNotBlank() && age != null) {
                val person = Person(firstName, lastName, age, position)
                employees.add(person)
                updateList()
                clearFields()
            } else {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        // Обработка нажатия на элемент списка для удаления
        listViewEmployees.setOnItemClickListener { _, _, position, _ ->
            employees.removeAt(position)
            updateList()
            Toast.makeText(this, "Сотрудник удален", Toast.LENGTH_SHORT).show()
        }
    }

    // Обновление списка сотрудников в ListView
    private fun updateList() {
        adapter.clear()
        adapter.addAll(employees.map { "${it.firstName} ${it.lastName} (${it.age}) - ${it.position}" })
    }

    // Очистка полей ввода
    private fun clearFields() {
        editFirstName.text.clear()
        editLastName.text.clear()
        editAge.text.clear()
    }

    // Создание меню (Exit)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Обработка выбора пункта меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_exit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}