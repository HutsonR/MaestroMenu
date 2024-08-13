package com.example.databasexmlcourse.core

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

/**
 * SecurePreferences - утилитный класс для работы с зашифрованными SharedPreferences.
 * Позволяет безопасно сохранять и извлекать данные различных типов.
 *
 * @param context Контекст приложения, необходимый для создания SharedPreferences.
 */
internal class SecurePreferences(context: Context) {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val sharedPreferences = EncryptedSharedPreferences.create(
        "secure_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    /**
     * Сохраняет значение в EncryptedSharedPreferences.
     *
     * @param key Ключ, по которому будет сохранено значение.
     * @param value Значение, которое необходимо сохранить (поддерживаются String, Int, Boolean, Float, Long).
     * @throws IllegalArgumentException Если тип данных не поддерживается.
     */
    fun <T> put(key: String, value: T) {
        with(sharedPreferences.edit()) {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                else -> throw IllegalArgumentException("Unsupported data type")
            }
            apply()
        }
    }

    /**
     * Извлекает значение из EncryptedSharedPreferences.
     *
     * @param key Ключ, по которому сохранялось значение.
     * @param defaultValue Значение по умолчанию, возвращаемое в случае отсутствия сохранённого значения.
     * @return Сохранённое значение или значение по умолчанию, если сохранённое значение отсутствует.
     * @throws IllegalArgumentException Если тип данных не поддерживается.
     */
    fun <T> get(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is String -> sharedPreferences.getString(key, defaultValue) as T
            is Int -> sharedPreferences.getInt(key, defaultValue) as T
            is Boolean -> sharedPreferences.getBoolean(key, defaultValue) as T
            is Float -> sharedPreferences.getFloat(key, defaultValue) as T
            is Long -> sharedPreferences.getLong(key, defaultValue) as T
            else -> throw IllegalArgumentException("Unsupported data type")
        }
    }

    /**
     * Удаляет значение из EncryptedSharedPreferences.
     *
     * @param key Ключ, по которому будет удалено значение.
     */
    fun remove(key: String) {
        with(sharedPreferences.edit()) {
            remove(key)
            apply()
        }
    }

    /**
     * Проверяет наличие значения в EncryptedSharedPreferences.
     *
     * @param key Ключ, наличие которого нужно проверить.
     * @return true, если значение по указанному ключу существует, иначе false.
     */
    fun contains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    /**
     * Очищает все значения в EncryptedSharedPreferences.
     */
    fun clear() {
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }
}