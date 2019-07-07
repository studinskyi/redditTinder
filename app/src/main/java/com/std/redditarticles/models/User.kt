package com.std.redditarticles.models

class User {
    var id: Long? = null
        private set
    var login: String? = null
    var password: String? = null
    var isAdministrator: Boolean = false
    var firstName: String? = null
    var surname: String? = null
    var secondName: String? = null
    var email: String? = null
    var phone: String? = null

    constructor(login: String,
                password: String,
                isAdministrator: Boolean?,
                firstName: String,
                surname: String,
                secondName: String,
                email: String,
                phone: String) {
        this.id = System.currentTimeMillis() + (Math.random() * 100).toInt() //(new Random()).nextInt(100)
        this.login = login
        this.password = password
        this.isAdministrator = isAdministrator!!
        this.firstName = firstName
        this.surname = surname
        this.secondName = secondName
        this.email = email
        this.phone = phone
    }

    constructor(id: Long,
                login: String,
                password: String,
                isAdministrator: Boolean?,
                firstName: String,
                surname: String,
                secondName: String,
                email: String,
                phone: String) {
        this.id = id
        this.login = login
        this.password = password
        this.isAdministrator = isAdministrator!!
        this.firstName = firstName
        this.surname = surname
        this.secondName = secondName
        this.email = email
        this.phone = phone
    }

    override fun equals(other: Any?): Boolean {
        if (other == null)
            return false

        if (other !is User)
            return false

        return this.id === other.id
    }

    override fun hashCode(): Int {
        return (21 + id!! * 41).toInt()
    }
}
