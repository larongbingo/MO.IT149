package org.moit149.connectly.users

import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, String> {
    fun findByExternalId(externalId: String): User?
}