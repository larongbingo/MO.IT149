package org.moit149.connectly.contactus

import org.springframework.data.mongodb.repository.MongoRepository

interface ContactRepository : MongoRepository<Contact, String>