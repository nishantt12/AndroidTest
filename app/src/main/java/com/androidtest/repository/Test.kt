package com.androidtest.repository

import io.reactivex.Single

fun main() {
    val test = Single.just("Hell")

    test
        .map {
            "feeelo"
        }
        .toObservable()
}