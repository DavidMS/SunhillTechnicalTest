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

public class SavingsAccountTest {
	private final static int NUM_THREADS = 10;
	private final static int NUM_ITERATIONS = 100;
	private final static double MONEY_INCREMENT = 10.0;
	
	BankAccountRepository repository = new BankAccountRepository();

	@Test
	public void depositOK() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
		SavingsAccount savingsAccount = new SavingsAccount();

		for (int i = 0; i < NUM_THREADS; i++) {
			executor.submit(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < NUM_ITERATIONS; i++) {		
							try {
								savingsAccount.deposit(MONEY_INCREMENT);
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
		assertEquals(NUM_THREADS * NUM_ITERATIONS * MONEY_INCREMENT, savingsAccount.balance, 0);
	}

	@Test(expected = NegativeDepositException.class)
	public void depositNegativeDepositError() throws NegativeDepositException {
		SavingsAccount savingsAccount = new SavingsAccount();
		
		savingsAccount.deposit(-10.0);
	}
	
	@Test
	public void getOwner() {
		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setOwner("Alberto");
		assertEquals("Alberto", savingsAccount.getOwner());
	}
	
	
	@Test
	public void getBalance() {
		SavingsAccount savingsAccount = new SavingsAccount();
		try {
			savingsAccount.deposit(100.0);
		} catch (NegativeDepositException e) {
			e.printStackTrace();
		}
		assertEquals(100.0, savingsAccount.getBalance(), 0);
	}
	
	@Test
	public void withdrawals() throws NegativeDepositException, InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.deposit(new Double(NUM_ITERATIONS * NUM_THREADS * MONEY_INCREMENT));

		for (int i = 0; i < NUM_THREADS; i++) {
			executor.submit(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < NUM_ITERATIONS; i++) {		
						try {
							savingsAccount.withdrawals(MONEY_INCREMENT);
						} catch (InsufficientMoneyException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
		}
		executor.shutdown();
		executor.awaitTermination(5, TimeUnit.SECONDS);
		assertEquals(0.0, savingsAccount.balance, 0);
	}
	
	@Test(expected = InsufficientMoneyException.class)
	public void withdrawalsInsufficientMoneyException() throws InsufficientMoneyException, NegativeDepositException {
		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.deposit(100.0);
		savingsAccount.withdrawals(new Double(150.0));
		
	}
	
	@Test
	public void getInterest() {
		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setInterest(1.5);
		assertEquals(1.5, savingsAccount.getInterest(), 0);
	}
	
	@Test
	public void payInterest() throws NegativeDepositException {
		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setInterest(1.5);
		savingsAccount.deposit(100.0);
		
		savingsAccount.payInterest();
		assertEquals(101.5, savingsAccount.getBalance(), 0);
	}
}
