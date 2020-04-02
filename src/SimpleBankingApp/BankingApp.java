package SimpleBankingApp;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random;

public class BankingApp {

	public static void main(String[] args) {
		// create scanner for user input
		Scanner nameScan = new Scanner(System.in);
		System.out.println("Please Enter Your Name: ");
		// store input into name variable
		String name = nameScan.next();
		// create a random id number
		Random rand = new Random();
		int newID = rand.nextInt(1000);
		BankAccount bankAcc = new BankAccount(name, Integer.toString(newID));
		bankAcc.showMenu();
		nameScan.close();
	}

}

// Bank account class
class BankAccount{
	// class variables
	int balance;
	int prevTransaction;
	String customerName;
	String customerID;
	
	// class constructor
	BankAccount(String custName, String custID){
		customerName = custName;
		customerID = custID;
		balance = 0;
		prevTransaction = 0;
	}
	
	void deposit(int amount) {
		if(amount > 0) {
			balance = balance + amount;
			prevTransaction = amount;
		}
	}
	
	void withdraw(int amount) {
		if(amount > 0) {
			balance = balance - amount;
			prevTransaction = -amount;
		}
	}
	
	void getPreviousTransaction() {
		if(prevTransaction > 0) {
			System.out.println("Deposited: " + prevTransaction);
		}
		else if(prevTransaction < 0) {
			System.out.println("Withdrawn: " + Math.abs(prevTransaction));
		}
		else{
			System.out.println("No Transaction Occured");
		}
	}
	
	void showMenu() {
		char option = '\0';
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Welcome "+customerName);
		System.out.println("Your ID is "+customerID);
		System.out.println("\n");
		
		do {
			System.out.println("==================");
			System.out.println("Enter an Option");
			System.out.println("A. Check Balance");
			System.out.println("B. Deposit");
			System.out.println("C. Withdraw");
			System.out.println("D. Previous Transaction");
			System.out.println("E. Exit");
			System.out.println("==================");
			try {
				option = Character.toUpperCase(scanner.next().charAt(0));
				switch(option) {
				case 'A':
					System.out.println("--------------------------");
					System.out.println("Balance = "+balance);
					System.out.println("--------------------------");
					break;
					
				case 'B':
					System.out.println("--------------------------");
					System.out.println("Enter Amount to Deposit: ");
					System.out.println("--------------------------");
					try {
						int amount = scanner.nextInt();
						deposit(amount);
					}
					catch(InputMismatchException ex){
						System.out.println("Invalid Amount, please enter a number");
						scanner.nextLine();
					}
					break;
					
				case 'C':
					System.out.println("--------------------------");
					System.out.println("Enter Amount to Withdraw: ");
					System.out.println("--------------------------");
					try {
						int amount1 = scanner.nextInt();
						withdraw(amount1);
					}
					catch(InputMismatchException ex){
						System.out.println("Invalid Amount, please enter a number");
						scanner.nextLine();
					}
					break;
					
				case 'D':
					System.out.println("--------------------------");
					getPreviousTransaction();
					System.out.println("--------------------------");
					break;
					
				case 'E':
					System.out.println("--------------------------");
					break;
					
				default:
					System.out.println("Invalid Option, Enter Another Option");
					System.out.println("\n");
					break;
					
				}
			}
			catch(InputMismatchException ex){
				System.out.println("Invalid Option, Please Enter A, B, C, D, or E");
				continue;
			}
			
		}
		while(option != 'E');
		
		System.out.println("Exiting Program");
		scanner.close();
	}
}
