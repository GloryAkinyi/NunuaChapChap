package com.glory.nunuachapchap.repository

import com.glory.nunuachapchap.data.UserDao
import com.glory.nunuachapchap.model.User

class UserRepository(private val userDao: UserDao) {
    suspend fun registerUser(user: User) {
        userDao.registerUser(user)
    }

    suspend fun loginUser(email: String, password: String): User? {
        return userDao.loginUser(email, password)
    }
}