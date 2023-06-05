package com.yasinenessisik.adopt_a_pet.fragments

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class AddPetFragmentTest{
    val addpetfragmenttest = AddPetFragment()
    @Test
    fun `image has right proportion`(){
        var result = addpetfragmenttest.resizeBitmapT(300,200)
        assertThat(result).isTrue()
    }
    @Test
    fun `image has false proportion`(){
        var result = addpetfragmenttest.resizeBitmapT(300,3000)
        assertThat(result).isFalse()
    }

}