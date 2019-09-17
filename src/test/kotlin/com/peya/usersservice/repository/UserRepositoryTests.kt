package com.peya.usersservice.repository

import com.peya.usersservice.model.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class UserRepositoryTests @Autowired constructor(
        val entityManager: TestEntityManager,
        val userRepository: UserRepository) {

    @Test
    fun `when findById then should return valid user`() {
        val user = User(firstName = "jorge", lastName = "cabrera")
        entityManager.persist(user)
        entityManager.flush()
        val found = userRepository.findById(user.id).get()
        assertThat(found).isNotNull
    }
}