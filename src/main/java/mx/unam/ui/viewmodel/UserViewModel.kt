package mx.unam.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.unam.database.UserEntity
import mx.unam.repository.UserRepository

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _users = MutableStateFlow<List<UserEntity>>(emptyList())
    val users: StateFlow<List<UserEntity>> = _users.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _showMessage = MutableStateFlow<String?>(null)
    val showMessage: StateFlow<String?> = _showMessage.asStateFlow()

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getAllUsers().collect { userList ->
                _users.value = userList
                _isLoading.value = false
            }
        }
    }

    fun addUser(name: String, email: String, age: Int) {
        viewModelScope.launch {
            if (name.isBlank() || email.isBlank()) {
                _showMessage.value = "Name and email cannot be empty"
                return@launch
            }

            val user = UserEntity(
                name = name,
                email = email,
                age = age
            )
            repository.insertUser(user)
            _showMessage.value = "User added successfully"
        }
    }

    fun updateUser(user: UserEntity, newName: String, newEmail: String, newAge: Int) {
        viewModelScope.launch {
            val updatedUser = user.copy(
                name = newName,
                email = newEmail,
                age = newAge
            )
            repository.updateUser(updatedUser)
            _showMessage.value = "User updated successfully"
        }
    }

    fun deleteUser(user: UserEntity) {
        viewModelScope.launch {
            repository.deleteUser(user)
            _showMessage.value = "User deleted successfully"
        }
    }

    fun clearMessage() {
        _showMessage.value = null
    }
}