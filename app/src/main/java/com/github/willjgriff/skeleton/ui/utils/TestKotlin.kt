package com.github.willjgriff.skeleton.ui.utils

import java.util.*

/**
 * Created by Will on 05/01/2017.
 */
fun convertDpToPixelKt(dp: Int): Int {
    val kotlin = 9

    println("Holea")

    val hola: Int = 9


    val holo: Pair<Int, Int>

    val comp = Comp(1984)

    var comps: List<Comp> = ArrayList()


    return 4
}

class PropertyExample() {
    var counter = 0
    var propertyWithCounter: Int? = null
        set(value) {
            counter++
            field = value
        }
}

data class Comp(val year: Int) : Comparable<Comp> {

    val lazey: Int by lazy {
        1
    }

//    val asd: Int?

    val lazyValue: String by lazy {
        println("computed!")
        "Hello"
    }


    override fun compareTo(other: Comp): Int {
        return if (year < other.year) -1 else 1
    }

//    fun checkInRange(year: Int): Boolean {
//        return year in Comp(year)
//    }

}
