package com.example.databasexmlcourse.features.feature_orders.util

enum class OrderStatus(val status: String) {
    AWAIT("не принят"),
    IN_PROGRESS("выполняется"),
    DONE("выполнен")
}