package com.peya.usersservice.domain.service

import com.peya.usersservice.application.dto.UserDto
import com.peya.usersservice.domain.entity.User
import com.peya.usersservice.domain.enums.UserStatus.ACTIVE
import com.peya.usersservice.domain.exception.UserNotFound
import com.peya.usersservice.domain.repository.UserRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull


@RunWith(SpringRunner::class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var userService: UserService

    @Before
    fun clear() {
        userRepository.deleteAll()
    }


    @Test
    fun `service should throw and exception when try to get a user that not exists`() {
        val userId = anyUserId()

        assertFailsWith<UserNotFound>{
            userService.get(userId)
        }
    }

    @Test
    fun `service can get an user`() {
        val saved = anySavedUser()

        val user = userService.get(saved.id)
        assertEquals(saved, user)
    }

    @Test
    fun `service can create an user`() {
        val userDto = anyUserDto()
        val created = userService.create(userDto)

        val saved = userService.get(created.id)
        assertEquals(saved.firstName, userDto.firstName)
        assertEquals(saved.lastName, userDto.lastName)
        assertEquals(saved.email, userDto.email)
        assertNotNull(saved.id)
        assertNotNull(saved.lastModifiedDate)
        assertNotNull(saved.createdDate)
        assertEquals(saved.status, ACTIVE)
    }

    @Test
    fun `service can not get a deleted user`() {
        val savedId = anySavedUser().id
        userService.delete(savedId)

        assertFailsWith<UserNotFound>{
            userService.get(savedId)
        }
    }

    @Test
    fun `service only get actives users`() {
        val active = anySavedUser()
        val deleted = anySavedUser()
        userService.delete(deleted.id)

        assertFailsWith<UserNotFound>{
            userService.get(deleted.id)
        }
        val user = userService.get(active.id)
        assertEquals(active, user)
    }

    @Test
    fun `service throw an exception when trying to delete an user that not exists`() {
        val userId = anyUserId()

        assertFailsWith<UserNotFound> {
            userService.delete(userId)
        }
    }

    @Test
    fun `service throw an exception when trying to update an user that not exists`() {
        val userId = anyUserId()
        val userDto = anyUserDto()

        assertFailsWith<UserNotFound> {
            userService.update(userId, userDto)
        }
    }

    @Test
    fun `service can update an user`() {
        val saved = anySavedUser()

        val newFirstName = "newFirstName"
        val newLastName = "newLastName"
        val newEmail = "newemail@test.com"
        val newPhone = "newPhone"
        val newUserDto = UserDto(firstName = newFirstName, lastName = newLastName, email = newEmail, phone = newPhone)
        userService.update(saved.id, newUserDto)
        val updated = userService.get(saved.id)

        assertEquals(updated.id, saved.id)
        assertEquals(updated.firstName, newFirstName)
        assertEquals(updated.lastName, updated.lastName)
        assertEquals(updated.email, updated.email)
        assertEquals(updated.status, ACTIVE)
        assertEquals(updated.createdDate, saved.createdDate)
        assert(updated.lastModifiedDate!! > saved.lastModifiedDate)
    }


    private fun anyUserId(): Long = 1
    private fun anyString(): String = "anyString"
    private fun anyEmail(): String = "user@test.com"
    private fun anyUserDto(): UserDto {
        val firstName = anyString()
        val lastName = anyString()
        val email = anyString()
        val phone = anyString()
        return UserDto(firstName = firstName, lastName = lastName, email = email, phone = phone)
    }
    private fun anySavedUser(): User {
        val user = User(firstName = anyString(), lastName = anyString(), email = anyEmail(), status = ACTIVE)
        return userRepository.save(user)
    }
}