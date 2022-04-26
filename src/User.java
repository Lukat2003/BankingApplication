public class User {
    
    private final String firstName;
    private final String lastName;
    private String PIN;
    private Account[] accounts = new Account[5];
    private byte numOfAccounts = 0;
    
    User(String firstName, String lastName, String PIN) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.setPIN(PIN);
    }

    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }

    public int getNumOfAccounts() {
        return numOfAccounts + 1;
    }

    public void setPIN(String newPIN) {
        this.PIN = newPIN;
    }

    public boolean isPIN(String num) {
        if (PIN.equals(num)) {
            return true;
        }
        else return false;
    }

    public void openNewAccount(int type, String name) throws InvalidInputException {
        if (numOfAccounts < 5) {
            accounts[numOfAccounts] = new Account((byte) type, name);
            numOfAccounts++;
            System.out.println(accounts[numOfAccounts]);
        }
        else System.out.println("Maximum account limit reached.");
    }

    public void printAccounts() {
        if (numOfAccounts > 0) {
            for (int i = 0; i < numOfAccounts; i++) {
                System.out.println("\t" + (i + 1) + ". " + accounts[i].toString());
                System.out.println("\t0. Cancel.");
            }
        }
        else System.out.println("\tYou have no accounts.\n");
    }

    public void deposit(double amount, int account, String currencyCode) throws InvalidInputException {
        if (account > -1 && account < numOfAccounts) {
            accounts[account].deposit(amount, currencyCode);
        }
        else { 
            System.out.println("ERROR: Invalid input.");
            throw new InvalidInputException();
        }
    }

    public void withdrawal(double amount, int account) throws InvalidInputException {
        if (account > -1 && account < numOfAccounts) {
            accounts[account].withdrawal(amount);
        }
        else {
            System.out.println("ERROR: Invalid input.");
            throw new InvalidInputException();
        }
    }

    public void transfer(double amount, int account, int other) throws InvalidInputException {
        if (account > -1 && account < numOfAccounts) {
            accounts[account].transfer(amount, accounts[other]);
        }
        else {
            System.out.println("ERROR: Invalid input.");
            throw new InvalidInputException();
        }
    }

    public String toString() {
        return lastName + ", " + firstName;
    }
}
