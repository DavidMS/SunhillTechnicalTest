package org.dms.sunhilltechnicaltest.model;

import org.dms.sunhilltechnicaltest.exceptions.InsufficientMoneyException;
import org.dms.sunhilltechnicaltest.exceptions.NegativeDepositException;

import lombok.extern.slf4j.Slf4j;

public abstract class AbstractBankAccount {

	protected String owner;
	protected double balance = 0.0;
	
	public synchronized double deposit(double amount) throws NegativeDepositException {
		System.out.println("Transfering " + amount + " to " + owner);
		if(amount < 0) {
			throw new NegativeDepositException("Negative deposits are not allowed");
		}
		this.balance += amount;
		System.out.println("The new balance is :" + balance);
		return balance;
	}
	
	public abstract double withdrawals(double amount)  throws InsufficientMoneyException;
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		System.out.println("Setting the owner for " + owner + " account");
		this.owner = owner;
	}
	
	public synchronized double getBalance() {
		return balance;
	}
}
