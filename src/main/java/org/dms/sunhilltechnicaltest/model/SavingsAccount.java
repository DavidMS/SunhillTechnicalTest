package org.dms.sunhilltechnicaltest.model;

import org.dms.sunhilltechnicaltest.exceptions.InsufficientMoneyException;

public class SavingsAccount extends AbstractBankAccount {

	private double interest;

	public SavingsAccount() {
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		System.out.println("Setting the interest for " + owner + " account.");
		System.out.println("The new interest is " + interest);
		this.interest = interest;
	}
	
	public double payInterest() {
		System.out.println("Paying the interest for " + owner + " account");
		this.balance += this.balance * this.interest / 100;
		System.out.println("The new balance is " + balance);
		return this.balance;
	}

	@Override
	public synchronized double withdrawals(double amount) throws InsufficientMoneyException {
		System.out.println("Performing a withdrawal from " + owner + " account.");
		if(this.balance < amount) {
			throw new InsufficientMoneyException("There is no enough money to perform the operation.");
		}
		this.balance -= amount;
		System.out.println("The new balance is " + balance);
		return this.balance;
	}
}
