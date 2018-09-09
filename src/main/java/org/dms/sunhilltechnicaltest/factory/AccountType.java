package org.dms.sunhilltechnicaltest.factory;

public enum AccountType {
	SAVINGS(1), CHECKING(2);
	
	private int numVal;

	AccountType(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}
