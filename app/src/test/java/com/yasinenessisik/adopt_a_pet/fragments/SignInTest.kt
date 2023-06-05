package com.yasinenessisik.adopt_a_pet.fragments


import com.google.common.truth.Truth.assertThat
import org.junit.Test

class SignInTest{
    private val signintest = SignIn()

    @Test
    fun `empty username returns false`(){
        val result = signintest.validateResitrationInput(
            " ",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun `empty password returns false`(){
        val result = signintest.validateResitrationInput(
            "enessisik@gmail.com",
            " ",
            "123"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun `password count less that seven returns false`(){
        val result = signintest.validateResitrationInput(
            "enessisik@gmail.com",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }


}