package org.dms.sunhilltechnicaltest.runner;

import java.util.Scanner;

import org.dms.sunhilltechnicaltest.Repository.BankAccountRepository;
import org.dms.sunhilltechnicaltest.factory.AccountType;
import org.dms.sunhilltechnicaltest.factory.BankAccountFactory;
import org.dms.sunhilltechnicaltest.model.CheckingsAccount;
import org.dms.sunhilltechnicaltest.model.SavingsAccount;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BankApplication implements CommandLineRunner {

	private int option;
	private Scanner reader = new Scanner(System.in);
	private String input = "";

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Bank Application started!");
		BankAccountRepository repository = new BankAccountRepository();
		CheckingsAccount checkingsAccount1 = (CheckingsAccount) BankAccountFactory.getBankAccount(AccountType.CHECKING, repository);
		CheckingsAccount checkingsAccount2 = (CheckingsAccount) BankAccountFactory.getBankAccount(AccountType.CHECKING, repository);
		CheckingsAccount checkingsAccount3 = (CheckingsAccount) BankAccountFactory.getBankAccount(AccountType.CHECKING, repository);
		CheckingsAccount checkingsAccount4 = (CheckingsAccount) BankAccountFactory.getBankAccount(AccountType.CHECKING, repository);
		
		checkingsAccount1.setOwner("checkingsOwner1");
		checkingsAccount1.setOverdraftLimit(100.0);
		
		checkingsAccount2.setOwner("checkingsOwner2");
		checkingsAccount2.setOverdraftLimit(200.0);
		
		checkingsAccount3.setOwner("checkingsOwner3");
		checkingsAccount3.setOverdraftLimit(300.0);
		
		checkingsAccount4.setOwner("checkingsOwner4");
		checkingsAccount4.setOverdraftLimit(400.0);
		
		SavingsAccount savingsAccount1 = (SavingsAccount) BankAccountFactory.getBankAccount(AccountType.SAVINGS, repository);
		SavingsAccount savingsAccount2 = (SavingsAccount) BankAccountFactory.getBankAccount(AccountType.SAVINGS, repository);
		SavingsAccount savingsAccount3 = (SavingsAccount) BankAccountFactory.getBankAccount(AccountType.SAVINGS, repository);
		SavingsAccount savingsAccount4 = (SavingsAccount) BankAccountFactory.getBankAccount(AccountType.SAVINGS, repository);
	
		savingsAccount1.setOwner("savingsOwner1");
		savingsAccount1.setInterest(4.5);
		
		savingsAccount2.setOwner("savingsOwner2");
		savingsAccount2.setInterest(5.5);
		
		savingsAccount3.setOwner("savingsOwner3");
		savingsAccount3.setInterest(6.5);
		
		savingsAccount4.setOwner("savingsOwner4");
		savingsAccount4.setInterest(7.5);
		
		repository.addAccount(1, checkingsAccount1);
		repository.addAccount(2, checkingsAccount1);
		repository.addAccount(3, checkingsAccount1);
		repository.addAccount(4, checkingsAccount1);
		
		repository.addAccount(5, savingsAccount1);
		repository.addAccount(6, savingsAccount2);
		repository.addAccount(7, savingsAccount3);
		repository.addAccount(8, savingsAccount4);
		
		checkingsAccount1.deposit(300.0);
		checkingsAccount2.deposit(200.0);
		checkingsAccount3.deposit(400.0);
		checkingsAccount4.deposit(500.0);
		
		savingsAccount1.deposit(200.0);
		savingsAccount2.deposit(300.0);
		savingsAccount3.deposit(400.0);
		savingsAccount4.deposit(500.0);
		
		savingsAccount1.payInterest();
		savingsAccount2.payInterest();
		savingsAccount3.payInterest();
		savingsAccount4.payInterest();
		
		savingsAccount1.withdrawals(200.0);
		savingsAccount3.withdrawals(400.0);
		
		checkingsAccount1.withdrawals(400.0);
		
		checkingsAccount2.cashTransfer(1, 200.0);
		
		System.out.println("Finishing Bank Application");
		
	
	}
}
