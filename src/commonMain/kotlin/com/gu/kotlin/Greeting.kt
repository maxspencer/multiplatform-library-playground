package com.gu.kotlin


class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}
