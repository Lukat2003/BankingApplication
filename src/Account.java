import java.util.HashMap;

public class Account {
    
    protected final int number;
    protected double balance;
    protected int type = 0;
    protected String name = "";
    protected HashMap<String, Double> currencies = new HashMap<>();

    Account(int type, String name) throws InvalidInputException {
        // Creates the HashMap of currency codes and their conversion values.
        currencies.put("USD", 1.0);
        currencies.put("GBP", 1.3086774);
        currencies.put("EUR", 1.09976);
        currencies.put("CAD", 0.783041);
        currencies.put("AUD", 0.733987);
        currencies.put("RUB", 0.00745057);
        currencies.put("CHF", 1.0747323);
        
        this.number = (int)((Math.random() * 999) + 111);
        this.type = type;
        this.setName(name);
    }

    public double getBalance() {
        return balance;
    }

    public int getNumber() {
        return number;
    }

    public int getType() {
        return type;
    }

    public String getTypeAString() {
        if (type == 0) {
            return "Checking";
        }
        if (type == 1) {
            return "Savings";
        }
        else return null;
    }

    public String getName() {
        return name;
    }

    public void setType(byte type) throws InvalidInputException {
        if (type == 0 || type == 1) {
            this.type = type;
        }
        else {
            System.out.println("Invalid type.");
            throw new InvalidInputException();
        }
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void deposit(Double amount, String currencyCode) throws InvalidInputException {
        if (currencies.containsKey(currencyCode)) {
            balance += (double) (amount * currencies.get(currencyCode));
            balance = Math.round(balance * 100.0) / 100.0;
        }
        else {
            System.out.println("Invalid currency code.");
            throw new InvalidInputException();
        }
    }

    public void deposit(int amount, String currencyCode) throws InvalidInputException {
        if (currencies.containsKey(currencyCode)) {
            balance += (double) (amount * currencies.get(currencyCode));
            balance = Math.round(balance * 100.0) / 100.0;
        }
        else {
            System.out.println("ERROR: Invalid currency code.");
            throw new InvalidInputException();
        }
    }

    public void withdrawal(Double amount) throws InvalidInputException {
        if (amount > 0) {
            if (amount > balance) {
                System.out.println("Insufficient funds.");
                throw new InvalidInputException();
            }
            else {
                balance -= amount;
            }
        }
        else {
            System.out.println("ERROR: Withdrawal amoung must be greater than 0.");
            throw new InvalidInputException();
        }
    }

    public void transfer(Double amount, Account other) throws InvalidInputException {
        other.withdrawal(amount);
        this.deposit(amount, "USD");
    }

    public int compareToBy(Account other, byte attribute) throws InvalidInputException {
        switch(attribute) {
            case 1:
            return Double.compare(this.getBalance(), other.getBalance());

            case 2:
            return Integer.compare(this.getNumber(), other.getNumber());

            case 3:
            return name.compareToIgnoreCase(other.getName());

            default:
            System.out.println("ERROR: Invalid attribute.");
            throw new InvalidInputException();
        }
    }

    public String toString() {
        String retVal = "";
        if (!name.equals("")) retVal += "\"" + name + "\" ";
        retVal += getTypeAString() + " " + number + " --> Balance: $" + getBalance();
        return retVal;
    }
}