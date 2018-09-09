package org.dms.sunhilltechnicaltest.model;

import org.dms.sunhilltechnicaltest.Repository.BankAccountRepository;
import org.dms.sunhilltechnicaltest.exceptions.AccountNotExistsException;
import org.dms.sunhilltechnicaltest.exceptions.InsufficientMoneyException;
import org.dms.sunhilltechnicaltest.exceptions.NegativeDepositException;

public class CheckingsAccount extends AbstractBankAccount {

	private double overdraftLimit;

	BankAccountRepository repository;

	public CheckingsAccount(BankAccountRepository repository) {
		this.repository = repository;
	}

	@Override
	public synchronized double withdrawals(double amount) throws InsufficientMoneyException {
		System.out.println("Performin a withdrawal of " + amount + " from " + owner);
		if (this.balance + overdraftLimit < amount) {
			throw new InsufficientMoneyException("There is no enough money to perform the operation.");
		}
		this.balance -= amount;
		System.out.println("The new balance is: " + balance);
		return this.balance;
	}

	public synchronized boolean cashTransfer(int accountId, double amount) throws AccountNotExistsException, NegativeDepositException { 	
		AbstractBankAccount account;
		account = repository.getAccount(accountId);
		System.out.println("Performing a cashTransfer between " + owner + " and " + account.getOwner());
		if(!(account instanceof CheckingsAccount)) {
			throw new AccountNotExistsException();
		}
		account.deposit(amount);
		System.out.println("The new balance of " + account.getOwner() + " is " + account.getBalance());
		return true;
	}

	public double getOverdraftLimit() {
		return overdraftLimit;
	}

	public void setOverdraftLimit(double overdraftLimit) {
		System.out.println("Setting the overdraft limit for " + owner);
		System.out.println("The new overdraft limit is " + overdraftLimit);
		this.overdraftLimit = overdraftLimit;
	}

}
