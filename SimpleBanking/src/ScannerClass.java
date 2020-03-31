import java.util.Scanner;

public class ScannerClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("enter number ");
		//int a = scanner.nextInt();
		//System.out.println("Value = "+a);
		
		char c = scanner.next().charAt(0);
		System.out.println("Value = "+c);
	}

}
