package com.ddoong2.javatokotlin.domain.user

interface UserRepositoryCustom {

    fun findAllWithHistories(): List<User>

}
