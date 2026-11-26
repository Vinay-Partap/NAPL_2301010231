package main

import "fmt"
var name string = "John Doe"

func classifyage(age int) string {
	if age < 13 {
		return "Child"
	} else if age < 20 {
		return "Teenager"
	}
	return "Adult"
}

func main() {
	// Variable declaration and initialization
	var name = "Alice"
	name = "Bob"
    fmt.Println("Hello, World!")
    fmt.Println("Name:", name)

	// Constants and iota
	const pi = 3.14
	fmt.Println("Value of pi:", pi)
	const (
		Monday = iota
		Tuesday
		Wednesday
		Thursday
		Friday
		Saturday
		Sunday
	)

	fmt.Println("Days of the week:")
	fmt.Println("Monday:", Monday)
	fmt.Println("Tuesday:", Tuesday)
	fmt.Println("Wednesday:", Wednesday)
	fmt.Println("Thursday:", Thursday)
	fmt.Println("Friday:", Friday)
	fmt.Println("Saturday:", Saturday)
	fmt.Println("Sunday:", Sunday)

	// Data types
	var age int = 25
	var height float64 = 5.9
	var isStudent bool = true
	var name2 string = "Charlie"
	character := 'A'
	runeValue := 'B'
	fmt.Println("Name:", name2)
	fmt.Println("Age:", age)
	fmt.Println("Height:", height)
	fmt.Println("Is Student:", isStudent)
	fmt.Println("Character:", character)
	fmt.Println("Rune Value:", runeValue)
	fmt.Println("Name:", name2)
	fmt.Println("Age:", age)
	fmt.Println("Height:", height)
	fmt.Println("Is Student:", isStudent)

	// Type conversion
	var num1 int = 10
	var num2 float64 = float64(num1)
	fmt.Println("Converted value:", num2)

	// Control structures
	if age >= 18 {
		fmt.Println("You are an adult.")
	} else {
		fmt.Println("You are a minor.")
	}

	fmt.Println("Age classification:", classifyage(25))

	// Switch statement
	
	switch age {
	case 18:
		fmt.Println("You are 18 years old.")
	case 25:
		fmt.Println("You are 25 years old.")
	default:
		fmt.Println("Age is not specified.")
	}

	// Loops
	for i := 0; i < 5; i++ {
		fmt.Println("Iteration:", i)
	}

	
}