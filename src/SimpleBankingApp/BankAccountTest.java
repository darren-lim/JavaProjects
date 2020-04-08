package SimpleBankingApp;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankAccountTest {

	private static BankAccount bankAcc = null;

	@BeforeEach
	void setUp() throws Exception {
		System.out.println("Setting Up");
		bankAcc = new BankAccount("TestUser", "1");
	}

	@AfterEach
	void tearDown() throws Exception {
		System.out.println("Tear Down");
	}

	@Test
	void testdeposit() {
		bankAcc.deposit(100);

		assertEquals(bankAcc.balance, 100);

		bankAcc.deposit(-100);

		assertEquals(bankAcc.balance, 100);

		bankAcc.deposit(0);

		assertEquals(bankAcc.balance, 100);
		// fail("Not yet implemented");
	}

	@Test
	void testwithdraw() {
		bankAcc.deposit(100);
		bankAcc.withdraw(50);

		assertEquals(bankAcc.balance, 50);

		bankAcc.withdraw(-100);

		assertEquals(bankAcc.balance, 50);

		bankAcc.withdraw(0);

		assertEquals(bankAcc.balance, 50);
		// fail("Not yet implemented");
	}

}
