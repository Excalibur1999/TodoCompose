package com.example.todocompose.data.repositories

import com.example.todocompose.data.TodoDao
import com.example.todocompose.data.models.TodoTask
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class TodoRepository @Inject constructor(
    private val todoDao: TodoDao
) {
    val getAllTasks: Flow<List<TodoTask>> = todoDao.getAllTodoTasks()
    val sortByLowPriority : Flow<List<TodoTask>> = todoDao.sortByLowPriority()
    val sortByHighPriority : Flow<List<TodoTask>> = todoDao.sortByHighPriority()

    fun getSelectedTask(taskId:Int):Flow<TodoTask> = todoDao.getSelectedTask(taskId)

    suspend fun addTask(todoTask: TodoTask){
        todoDao.insertTodo(todoTask)
    }

    suspend fun deleteTask(todoTask: TodoTask){
        todoDao.deleteTask(todoTask)
    }

    suspend fun deleteAllTask(){
        todoDao.deleteAllTasks()
    }

    fun searchDatabase(searchQuery:String):Flow<List<TodoTask>> =
        todoDao.searchDatabase(searchQuery)

    suspend fun updateTask(todoTask: TodoTask){
        todoDao.updateTask(todoTask)
    }

}