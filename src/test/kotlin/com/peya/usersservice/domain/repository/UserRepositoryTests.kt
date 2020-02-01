package com.peya.usersservice.domain.repository

import com.peya.usersservice.application.dto.UserDto
import com.peya.usersservice.domain.entity.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@DataJpaTest
@EnableJpaAuditing
class UserRepositoryTests @Autowired constructor(
        val entityManager: TestEntityManager,
        val userRepository: UserRepository) {

    @Test
    fun `when findById then should return valid user`() {
        val user = User(firstName = "jorge", lastName = "cabrera")
        entityManager.persist(user)
        entityManager.flush()
        val found = userRepository.findById(user.id)
        assertThat(found).isNotNull
        assertThat(found?.createdDate).isNotNull()
        assertThat(found?.lastModifiedDate).isNotNull()
    }

    @Test
    fun `when save userDto as user then should work ok`() {
        val userDto = UserDto(firstName = "jorge",lastName = "cabrera")
        val saveUser = entityManager.persistAndFlush(userDto.toUser())
        val found = userRepository.findById(saveUser.id)
        assertThat(found).isNotNull
        assertThat(found?.createdDate).isNotNull()
        assertThat(found?.lastModifiedDate).isNotNull()
    }
}