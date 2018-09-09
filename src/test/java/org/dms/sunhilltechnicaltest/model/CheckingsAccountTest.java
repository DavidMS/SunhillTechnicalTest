package org.dms.sunhilltechnicaltest.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.dms.sunhilltechnicaltest.Repository.BankAccountRepository;
import org.dms.sunhilltechnicaltest.exceptions.AccountNotExistsException;
import org.dms.sunhilltechnicaltest.exceptions.DuplicateAccountException;
import org.dms.sunhilltechnicaltest.exceptions.InsufficientMoneyException;
import org.dms.sunhilltechnicaltest.exceptions.NegativeDepositException;
import org.junit.Test;

public class CheckingsAccountTest {

	private final static int NUM_THREADS = 11;
	private final static int NUM_ITERATIONS = 100;
	private final static double MONEY_INCREMENT = 10.0;
	
	BankAccountRepository repository = new BankAccountRepository();

	@Test
	public void depositOK() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
		CheckingsAccount checkingAccount = new CheckingsAccount(repository);

		for (int i = 0; i < NUM_THREADS; i++) {
			executor.submit(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < NUM_ITERATIONS; i++) {		
							try {
								checkingAccount.deposit(MONEY_INCREMENT);
							} catch (NegativeDepositException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				}
			});
		}
		executor.shutdown();
		executor.awaitTermination(5, TimeUnit.SECONDS);
		assertEquals(NUM_THREADS * NUM_ITERATIONS * MONEY_INCREMENT, checkingAccount.balance, 0);
	}

	@Test(expected = NegativeDepositException.class)
	public void depositNegativeDepositError() throws NegativeDepositException {
		CheckingsAccount checkingAccount = new CheckingsAccount(repository);
		
		checkingAccount.deposit(-10.0);
	}
	
	@Test
	public void getOwner() {
		CheckingsAccount checkingAccount = new CheckingsAccount(repository);
		checkingAccount.setOwner("Alberto");
		assertEquals("Alberto", checkingAccount.getOwner());
	}
	
	
	@Test
	public void getBalance() {
		CheckingsAccount checkingAccount = new CheckingsAccount(repository);
		try {
			checkingAccount.deposit(100.0);
		} catch (NegativeDepositException e) {
			e.printStackTrace();
		}
		assertEquals(100.0, checkingAccount.getBalance(),0);
	}
	
	@Test
	public void withdrawals() throws NegativeDepositException, InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
		CheckingsAccount checkingAccount = new CheckingsAccount(repository);
		checkingAccount.setOverdraftLimit(1000.0);
		checkingAccount.deposit(new Double(NUM_ITERATIONS * (NUM_THREADS - 1) * MONEY_INCREMENT));

		for (int i = 0; i < NUM_THREADS; i++) {
			executor.submit(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < NUM_ITERATIONS; i++) {		
						try {
							checkingAccount.withdrawals(MONEY_INCREMENT);
						} catch (InsufficientMoneyException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
		}
		executor.shutdown();
		executor.awaitTermination(6, TimeUnit.SECONDS);
		assertEquals(-1000.0, checkingAccount.getBalance(), 0);
	}
	
	@Test(expected = InsufficientMoneyException.class)
	public void withdrawalsInsufficientMoneyException() throws InsufficientMoneyException {
		CheckingsAccount checkingAccount = new CheckingsAccount(repository);
		checkingAccount.setOverdraftLimit(100.0);
		checkingAccount.withdrawals(150.0);
		
	}
	
	@Test
	public void cashTransfer() throws AccountNotExistsException, DuplicateAccountException, NegativeDepositException {
		
		CheckingsAccount account1 = new CheckingsAccount(repository);
		account1.setOverdraftLimit(new Double(100.0));
		account1.setOwner("Alberto");
		int ACCOUNT1_ID = 1;
		
		CheckingsAccount account2 = new CheckingsAccount(repository);
		account2.setOverdraftLimit(new Double(100.0));
		account2.setOwner("Pedro");
		int ACCOUNT2_ID = 2;
		
		repository.addAccount(ACCOUNT1_ID, account1);
		repository.addAccount(ACCOUNT2_ID, account2);
		
		boolean result = account1.cashTransfer(ACCOUNT2_ID, 100.0);
		assertEquals(100.0, account2.getBalance(), 0);
		assertTrue(result);
	}
	
	@Test(expected = AccountNotExistsException.class)
	public void cashTransferAccountDoenstExists() throws DuplicateAccountException, AccountNotExistsException, NegativeDepositException {
		
		CheckingsAccount account1 = new CheckingsAccount(repository);
		account1.setOverdraftLimit(new Double(100.0));
		account1.setOwner("Alberto");
		int ACCOUNT1_ID = 1;
		
		CheckingsAccount account2 = new CheckingsAccount(repository);
		account2.setOverdraftLimit(new Double(100.0));
		account2.setOwner("Pedro");
		int ACCOUNT2_ID = 2;
		
		repository.addAccount(ACCOUNT1_ID, account1);
		
		boolean result = account1.cashTransfer(ACCOUNT2_ID, 100.0);
	}
	
	@Test(expected = NegativeDepositException.class)
	public void cashTransferNegativeDepositException() throws AccountNotExistsException, DuplicateAccountException, NegativeDepositException {
		
		CheckingsAccount account1 = new CheckingsAccount(repository);
		account1.setOverdraftLimit(new Double(100.0));
		account1.setOwner("Alberto");
		int ACCOUNT1_ID = 1;
		
		CheckingsAccount account2 = new CheckingsAccount(repository);
		account2.setOverdraftLimit(new Double(100.0));
		account2.setOwner("Pedro");
		int ACCOUNT2_ID = 2;
		
		repository.addAccount(ACCOUNT1_ID, account1);
		repository.addAccount(ACCOUNT2_ID, account2);
		
		boolean result = account1.cashTransfer(ACCOUNT2_ID, -100.0);
	}


	@Test
	public void getOverdraftLimit() {
		CheckingsAccount account1 = new CheckingsAccount(repository);
		account1.setOverdraftLimit(new Double(100.0));
		account1.setOwner("Alberto");
		int ACCOUNT1_ID = 1;
		
		assertEquals(100.0, account1.getOverdraftLimit(), 0);
	}

}
