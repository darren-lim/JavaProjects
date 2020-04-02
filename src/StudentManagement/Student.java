package StudentManagement;

import java.util.Scanner;

public class Student {
	// Class variables
	private String firstName;
	private String lastName;
	private int gradeYear;
	private String studentID;
	private String courses = "";
	private int tuitionBalance = 0;
	private static int costOfCourse = 600;
	private static int id = 1000;
	
	// Constructor:
	public Student() {
		Scanner in = new Scanner(System.in);
		
		System.out.print("Enter student first name: ");
		this.firstName = in.nextLine();
		
		System.out.print("Enter student last name: ");
		this.lastName = in.nextLine();
		
		System.out.print("1 - Freshman\n2 - Sophomore\n3 - Junior\n4 - Senior\nEnter student grade level: ");
		this.gradeYear = in.nextInt();
		
		setStudentID();
	}
	
	// Generate an ID
	// Grade level + ID
	private void setStudentID() {
		id++;
		this.studentID = gradeYear + "" + id;
	}
	
	// Enroll in courses
	public void enroll() {
		// Get inside a loop, user hits 0 when done enrolling
		do {
			System.out.print("Enter course to enroll (0 to quit): ");
			Scanner in = new Scanner(System.in);
			String course = in.nextLine();
			if (course.equals("Q")) {
				break;
			}
			else {
				courses = courses + "\n " + course;
				tuitionBalance += costOfCourse;
			}
		}while (true);
	}
	
	// View Balance
	public void viewBalance() {
		System.out.println("Your balance is: $"+tuitionBalance);
	}
	
	// Pay Tuition
	public void payTuition() {
		viewBalance();
		System.out.print("Enter your payment: $");
		Scanner payIn = new Scanner(System.in);
		int payment = payIn.nextInt();
		tuitionBalance -= payment;
		System.out.println("Thank you for your payment of $:" +payment);
		viewBalance();
	}
	
	// Show Status
	public String toString() {
		return "Name: " + firstName + " " + lastName + 
				"\nGrade level: " + gradeYear +
				"\nStudent ID: " + studentID +
				"\nCourses Enrolled: " + courses +
				"\nBalance: $" + tuitionBalance;
	}

}
