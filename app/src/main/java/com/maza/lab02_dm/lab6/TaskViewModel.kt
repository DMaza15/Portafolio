package com.maza.lab02_dm.lab6

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TaskViewModel : ViewModel() {

    //Estado privado mutable
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    //Estado publico inmutable expuesto a la UI
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private var nextId = 1

    fun addTask(title: String){
        if (title.isNotBlank()){
            _tasks.update { currentList ->
                currentList + Task(id = nextId++, title = title)
            }
        }
    }

    fun toggleTaskCompletion(taskId: Int) {
        _tasks.update { currentList ->
            currentList.map { task ->
                if (task.id == taskId) task.copy(isCompleted = !task.isCompleted)
                else task
            }
        }
    }
    fun deleteTask(taskId: Int) {
        _tasks.update { currentList ->
            currentList.filter { it.id != taskId }
        }
    }
}

