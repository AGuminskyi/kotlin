// SKIP_ERRORS_AFTER
class A {
    fun unsafeCall() {}
}

fun unsafe() {
    val a: A? = null
    a<caret>!!.unsafeCall()
}