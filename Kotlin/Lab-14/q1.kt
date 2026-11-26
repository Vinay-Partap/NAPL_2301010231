
// Lab 14 - Q1
// Collections and Functional Transformations

data class Student(
    val rollNo: Int,
    val name: String,
    val percentage: Double,
    val department: String
)

fun main() {

    // 1. LIST COLLECTIONS

    println("===== LIST COLLECTIONS =====")

    // Read-only List
    val readOnlyList = listOf(10, 20, 30, 40)
    println("Read-only List: $readOnlyList")
    println("Contains 20: ${readOnlyList.contains(20)}")
    println("Element at index 2: ${readOnlyList[2]}")

    // Mutable List
    val mutableList = mutableListOf(10, 20, 30)
    mutableList.add(40)
    mutableList.remove(20)
    println("Mutable List after add and remove: $mutableList")
    println("Contains 30: ${mutableList.contains(30)}")
    println("Element at index 1: ${mutableList[1]}")


    // 2. SET COLLECTIONS

    println("\n===== SET COLLECTIONS =====")

    // Read-only Set
    val readOnlySet = setOf(10, 20, 30, 30, 40)
    println("Read-only Set: $readOnlySet")
    println("Contains 20: ${readOnlySet.contains(20)}")

    // Mutable Set
    val mutableSet = mutableSetOf(10, 20, 30)
    mutableSet.add(40)
    mutableSet.remove(20)
    println("Mutable Set after add and remove: $mutableSet")
    println("Contains 30: ${mutableSet.contains(30)}")


    // 3. MAP COLLECTIONS

    println("\n===== MAP COLLECTIONS =====")

    // Read-only Map
    val readOnlyMap = mapOf(
        101 to "Aman",
        102 to "Rahul",
        103 to "Priya"
    )

    println("Read-only Map: $readOnlyMap")
    println("Student with roll 102: ${readOnlyMap[102]}")
    println("Contains key 101: ${readOnlyMap.containsKey(101)}")

    // Mutable Map
    val mutableMap = mutableMapOf(
        101 to "Aman",
        102 to "Rahul"
    )

    mutableMap[103] = "Priya"
    mutableMap.remove(102)

    println("Mutable Map after add and remove: $mutableMap")
    println("Student with roll 101: ${mutableMap[101]}")
    println("Contains key 103: ${mutableMap.containsKey(103)}")


    // 4. FUNCTIONAL TRANSFORMATIONS

    println("\n===== FUNCTIONAL TRANSFORMATIONS =====")

    val numbers = listOf(1, 2, 3, 4, 5)

    // map: Multiply each number by 2
    val mappedNumbers = numbers.map { it * 2 }
    println("Original List: $numbers")
    println("After map (*2): $mappedNumbers")

    // filter: Select even numbers
    val filteredNumbers = numbers.filter { it % 2 == 0 }
    println("After filter (even): $filteredNumbers")

    // reduce: Calculate sum
    val sum = numbers.reduce { total, number ->
        total + number
    }
    println("After reduce (sum): $sum")


    // 5. SORTING STUDENT OBJECTS

    println("\n===== SORTING STUDENTS =====")

    val students = listOf(
        Student(101, "Aman", 85.5, "CSE"),
        Student(102, "Rahul", 72.0, "IT"),
        Student(103, "Priya", 92.5, "CSE"),
        Student(104, "Neha", 88.0, "IT"),
        Student(105, "Karan", 65.0, "ECE")
    )

    // Sort students by percentage in descending order
    val sortedStudents = students.sortedByDescending { it.percentage }

    println("Students sorted by percentage:")
    sortedStudents.forEach {
        println("${it.name} - ${it.percentage}%")
    }


    // 6. GROUPING COLLECTIONS

    println("\n===== GROUPING STUDENTS BY DEPARTMENT =====")

    val groupedStudents = students.groupBy { it.department }

    groupedStudents.forEach { (department, studentList) ->
        println("$department:")
        studentList.forEach {
            println("  ${it.name} - ${it.percentage}%")
        }
    }
}
