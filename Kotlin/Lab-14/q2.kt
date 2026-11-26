
// Lab 14 - Q2
// Null Safety: Nullable Types, Safe Calls and Elvis Operator

fun main() {

    // 1. Nullable and Non-Nullable Variables

    println("===== NULLABLE AND NON-NULLABLE VARIABLES =====")

    var name: String = "Vinay"
    var nickname: String? = null

    println("Non-nullable String: $name")
    println("Nullable String: $nickname")

    // name = null
    // Compile-time error because String cannot store null.

    nickname = "Vicky"
    println("Updated Nullable String: $nickname")


    // 2. Safe Call Operator (?.)

    println("\n===== SAFE CALL OPERATOR =====")

    var message: String? = "Hello Kotlin"

    println("Message Length: ${message?.length}")

    message = null
    println("Message Length when null: ${message?.length}")


    // 3. Elvis Operator (?:)

    println("\n===== ELVIS OPERATOR =====")

    val userName: String? = null

    val displayName = userName ?: "Guest"

    println("Display Name: $displayName")


    // 4. Not-null Assertion Operator (!!)

    println("\n===== NOT-NULL ASSERTION OPERATOR =====")

    val text: String? = null

    try {
        val result = text!!.length
        println("Text Length: $result")
    } catch (e: NullPointerException) {
        println("Exception Caught: ${e::class.simpleName}")
        println("Message: Cannot access length of a null value")
    }

    println("\nProgram executed successfully.")
}
