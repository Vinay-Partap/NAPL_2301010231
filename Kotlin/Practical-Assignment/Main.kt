
// Kotlin Practical Assignment
// Domain Model with Extensions and DSL

// 1. Domain Model

data class Student(
    val rollNo: Int,
    val name: String,
    val email: String?
)

data class Course(
    val courseId: Int,
    val title: String,
    val fee: Double,
    val mentor: String?
)

data class Registration(
    val student: Student,
    val course: Course,
    val marks: Int?
)



// 2. Extension Functions

// Extension function for Student
fun Student.getStudentInfo(): String {
    return "Roll No: $rollNo, Name: $name, Email: ${email ?: "Not Provided"}"
}


// Extension function for Registration
fun Registration.isPassed(): Boolean {
    return (marks ?: 0) >= 40
}

// 3. DSL for Report Generation

class ReportBuilder {
    var title: String = "Course Registration Report"
    var showStudents: Boolean = true
    var showFees: Boolean = true

    fun build(registrations: List<Registration>): String {
        val report = StringBuilder()

        report.appendLine("========== $title ==========")

        if (showStudents) {
            report.appendLine("\nRegistered Students:")

            registrations.forEach {
                report.appendLine(it.student.getStudentInfo())
            }
        }

        if (showFees) {
            val totalFees = registrations
                .map { it.course.fee }
                .reduce { total, fee -> total + fee }

            report.appendLine("\nTotal Collected Fees: Rs. $totalFees")
        }

        return report.toString()
    }
}

// DSL function
fun registrationReport(block: ReportBuilder.() -> Unit): ReportBuilder {
    return ReportBuilder().apply(block)
}

// 4. Main Function

fun main() {

    // Creating students
    val s1 = Student(101, "Aman", "aman@gmail.com")
    val s2 = Student(102, "Rahul", null)
    val s3 = Student(103, "Priya", "priya@gmail.com")

    // Creating courses
    val c1 = Course(1, "Kotlin", 5000.0, "Mr. Sharma")
    val c2 = Course(2, "Java", 4000.0, null)
    val c3 = Course(3, "Python", 6000.0, "Ms. Mehta")

    // Creating registrations
    val registrations = listOf(
        Registration(s1, c1, 85),
        Registration(s2, c2, 35),
        Registration(s3, c1, 75),
        Registration(s1, c3, null)
    )

    println("===== COURSE REGISTRATION SYSTEM =====")

    // Null-safe properties
    println("\nCourse Mentors:")
    registrations.forEach {
        println("${it.course.title}: ${it.course.mentor ?: "Not Assigned"}")
    }

    // Functional Transformation: map
    println("\n===== MAP: STUDENT NAMES =====")
    val studentNames = registrations.map { it.student.name }
    println(studentNames)

    // Functional Transformation: filter
    println("\n===== FILTER: PASSED STUDENTS =====")
    val passedStudents = registrations.filter { it.isPassed() }
    passedStudents.forEach {
        println("${it.student.name} - ${it.marks} marks")
    }

    // Functional Transformation: reduce
    println("\n===== REDUCE: TOTAL COURSE FEES =====")
    val totalFees = registrations
        .map { it.course.fee }
        .reduce { total, fee -> total + fee }

    println("Total Fees: Rs. $totalFees")

    // Functional Transformation: groupBy
    println("\n===== GROUP BY: COURSE =====")
    val groupedCourses = registrations.groupBy { it.course.title }

    groupedCourses.forEach { (course, entries) ->
        println("$course: ${entries.size} registrations")
    }

    // Extension Function
    println("\n===== EXTENSION FUNCTION =====")
    println(s1.getStudentInfo())

    // DSL Report Generation
    println("\n===== DSL REPORT =====")

    val report = registrationReport {
        title = "Student Course Report"
        showStudents = true
        showFees = true
    }

    print(report.build(registrations))
}
