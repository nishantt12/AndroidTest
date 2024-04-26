package com.androidtest.presistence

import android.util.Log
import io.reactivex.Single

class Test {


}

fun main(){
    val testList = listOf<String>()
    val testSingle = Single.just(testList)

    testSingle
        .flattenAsObservable {
            println( "no error")
            it
        }
        .map {
            println( "no error2")

            it.length
        }
        .toList()
        .onErrorReturn {
            println( "error")
            emptyList<Int>()
        }

}