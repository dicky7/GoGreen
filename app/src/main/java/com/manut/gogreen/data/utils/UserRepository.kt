package com.manut.gogreen.data.utils

class UserRepository(private val sesi: SessionManager) {

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(sesi: SessionManager): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(sesi)
            }
    }

    fun loginUser(username: String) {
        sesi.createLoginSession()
        sesi.saveToPreference(SessionManager.KEY_EMAIL, username)
    }

    fun getUser() = sesi.getFromPreference(SessionManager.KEY_EMAIL)

    fun isUserLogin() = sesi.isLogin

    fun logoutUser() = sesi.logout()
}