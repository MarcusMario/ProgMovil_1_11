package mx.unam.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch

import mx.unam.R
import mx.unam.database.UserDatabase
import mx.unam.database.UserEntity
import mx.unam.databinding.ActivityMainBinding
import mx.unam.repository.UserRepository
import mx.unam.ui.adapter.UserAdapter
import mx.unam.ui.viewmodel.UserViewModel
import mx.unam.ui.viewmodel.UserViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter

    private val viewModel: UserViewModel by viewModels {
        val database = UserDatabase.getDatabase(this)
        val repository = UserRepository(database.userDao())
        UserViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter(
            onEditClick = { user ->
                showEditDialog(user)
            },
            onDeleteClick = { user ->
                deleteUser(user)
            }
        )

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter
    }

    private fun setupObservers() {

        lifecycleScope.launch {
            viewModel.users.collect { users ->
                adapter.submitList(users)
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                binding.progressBar.visibility =
                    if (isLoading) android.view.View.VISIBLE
                    else android.view.View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.showMessage.collect { message ->
                message?.let {
                    Toast.makeText(
                        this@MainActivity,
                        it,
                        Toast.LENGTH_SHORT
                    ).show()

                    viewModel.clearMessage()
                }
            }
        }
    }

    private fun setupClickListeners() {

        binding.btnAdd.setOnClickListener {

            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val age = binding.etAge.text.toString().toIntOrNull() ?: 0

            viewModel.addUser(
                name,
                email,
                age
            )

            clearInputs()
        }
    }

    private fun showEditDialog(user: UserEntity) {

        val dialogView =
            layoutInflater.inflate(
                R.layout.dialog_edit_user,
                null
            )

        val etName =
            dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(
                R.id.etEditName
            )

        val etEmail =
            dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(
                R.id.etEditEmail
            )

        val etAge =
            dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(
                R.id.etEditAge
            )

        etName.setText(user.name)
        etEmail.setText(user.email)
        etAge.setText(user.age.toString())

        AlertDialog.Builder(this)
            .setTitle("Edit User")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->

                val newName = etName.text.toString()
                val newEmail = etEmail.text.toString()
                val newAge = etAge.text.toString().toIntOrNull() ?: 0

                viewModel.updateUser(
                    user,
                    newName,
                    newEmail,
                    newAge
                )
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteUser(user: UserEntity) {

        AlertDialog.Builder(this)
            .setTitle("Delete User")
            .setMessage(
                "Are you sure you want to delete ${user.name}?"
            )
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteUser(user)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun clearInputs() {

        binding.etName.text?.clear()
        binding.etEmail.text?.clear()
        binding.etAge.text?.clear()
    }
}