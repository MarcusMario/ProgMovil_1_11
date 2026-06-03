# Ejercicio 11 - Room CRUD App - Android (MVVM + Room + Kotlin)

Aplicación Android desarrollada en Kotlin que implementa un sistema CRUD de usuarios utilizando **Room Database**, arquitectura **MVVM**, **ViewModel**, **Coroutines** y **RecyclerView**.

---

##  Descripción del proyecto

Aplicación Android desarrollada en Kotlin que implementa un sistema CRUD completo de usuarios usando **Room Database** y arquitectura **MVVM**, con manejo reactivo de datos mediante **Coroutines + Flow/LiveData**.

El proyecto simula un sistema básico de gestión de usuarios con persistencia local, siguiendo buenas prácticas de separación de capas.

---

## Funcionalidades

-  Crear usuarios (nombre, email, edad)
-  Listar usuarios en tiempo real con RecyclerView
-  Editar usuarios mediante AlertDialog
-  Eliminar usuarios con confirmación
-  Actualización automática de la interfaz (Flow/LiveData)
-  Persistencia local con Room Database

---

##  Arquitectura del proyecto

El proyecto sigue el patrón **MVVM (Model - View - ViewModel)**:

UI (Activity / XML)
↓
ViewModel
↓
Repository
↓
Room (DAO)
↓
SQLite Database


---

##  Tecnologías utilizadas

- Kotlin
- Android SDK
- Room Database
- ViewModel (AndroidX Lifecycle)
- LiveData / StateFlow
- Coroutines
- RecyclerView
- ViewBinding
- Material Design Components

---

##  Estructura del proyecto

```

mx.unam/
│
├── database/
│   ├── UserDatabase.kt
│   ├── UserDao.kt
│   └── UserEntity.kt
│
├── repository/
│   └── UserRepository.kt
│
├── ui/
│   ├── activities/
│   │   └── MainActivity.kt
│   ├── adapter/
│   │   └── UserAdapter.kt
│   └── viewmodel/
│       ├── UserViewModel.kt
│       └── UserViewModelFactory.kt
│
└── MainActivity (UI principal)


```

---

##  Capturas

<img width="456" height="991" alt="image" src="https://github.com/user-attachments/assets/3de78965-d011-4199-b3c9-aab7cd242f77" />

<img width="456" height="991" alt="image" src="https://github.com/user-attachments/assets/2fea5c0e-4392-474f-972e-345dd7ca70b3" />

<img width="444" height="680" alt="image" src="https://github.com/user-attachments/assets/2e54d983-48c7-40d4-8472-c11e0fe4f5f6" />



---

##  Conceptos aplicados

Este proyecto refuerza:

-  Arquitectura MVVM en Android
-  Persistencia local con Room
-  Programación reactiva (Flow / LiveData)
-  Corrutinas y manejo de hilos
-  Repository pattern
-  Separación de responsabilidades
-  UI reactiva con RecyclerView

---

##  Problemas resueltos durante el desarrollo

- Incompatibilidad JVM (Java 11 vs Kotlin 21)
- Error KSP en generación de código
- Configuración incorrecta de AndroidX
- Conflictos de versiones entre Room + Kotlin + AGP
- Problemas con ViewBinding y lifecycleScope

---

