// SKIP_ERRORS_AFTER
class A {
    fun check() {}
}

var global: A? = null

fun main() {
    global!!<caret>.check()
}