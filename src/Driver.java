import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

    private static ArrayList<User> userDatabase = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        userDatabase.add(new User("Luka", "Teodorovic", "0000"));
        userDatabase.get(0).openNewAccount(0, "");
        //userDatabase.get(0).openNewAccount(0, "");
        ATM(input);
    }

    public static void ATM(Scanner input) {
        System.out.print("Welcome, please enter your PIN to continue: ");
        boolean done = false;
        while (!done) {
            try {
                done = loginScreen(input);
            }
            catch (InvalidInputException e) {
                System.out.print("Re-enter PIN: ");
            }
        }
    }

    public static boolean loginScreen(Scanner input) throws InvalidInputException {
        System.out.print("> ");
        String PIN = input.next();
        if (PIN.length() != 4) {
            System.out.println("\nInvalid PIN.");
            throw new InvalidInputException();
        }
        else {
            for (int i = 0; i < userDatabase.size(); i++) {
                if (userDatabase.get(i).isPIN(PIN)) {
                    System.out.println("\nWelcome back, " + userDatabase.get(i).getFirstName());
                    menuScreen(input, userDatabase.get(i));
                    return true;
                }
            }
            System.out.println("\nPIN does not exist in our records.");
            throw new InvalidInputException();
        }
    }

    public static void menuScreen(Scanner input, User user) {
        boolean done = false;
        while (!done) {
            System.out.println("\nWhat would you like to do?\n\n" + 
                "\t1. Deposit." +
                "\t2. Witdrawal." +
                "\t3. Transfer." +
                "\t4. Open a new account." +
                "\t0. Exit.");
            
            System.out.print("\n> ");
            int userInput = input.nextInt();

            switch (userInput) {
                case 1:
                actionScreen(input, user, "deposit");
                break;

                case 2:
                actionScreen(input, user, "withdrawal");
                break;

                case 3:
                actionScreen(input, user, "transfer");

                case 0:
                System.out.println("\nThank you for banking with us!");
                done = true;
            }
        }
    }

    public static void actionScreen(Scanner input, User user, String action) {
        if (user.getNumOfAccounts() != 0) {
            System.out.println("\nFor which account would you like to make a " + action + "?\n");
            user.printAccounts();
            
            System.out.print("\n> ");
            int userInput = input.nextInt();

            if (userInput == 0) {
                menuScreen(input, user);
            }
            if (userInput > user.getNumOfAccounts()) {
                System.out.println("ERROR: Invalid input.\n");
                menuScreen(input, user);
            }

            System.out.print("\nPlease enter the amount you would like to " + action + ":\n");
            System.out.print("\n> ");
            int amount = input.nextInt();

            if (action.equals("deposit")) {
                System.out.println("\nPlease enter the currency code:");
                System.out.print("\n> ");
                String code = input.next();

                try {
                    user.deposit(amount, userInput - 1, code);
                    System.out.println("\nDeposit successful!");
                    return;
                } catch (InvalidInputException e) {
                    actionScreen(input, user, action);
                }
            }
            else if (action.equals("withdrawal")) {
                try {
                    user.withdrawal(amount, userInput);
                    System.out.println("\nWithdrawal successful!");
                    return;
                } catch (InvalidInputException e) {
                    actionScreen(input, user, action);
                }
            }
            else if (action.equals("transfer")) {
                System.out.println("\nWhich account would you like to transfer to?");
                System.out.println("\n>");
                int other = input.nextInt();

                try {
                    user.transfer(amount, userInput, other);
                    System.out.println("Transfer successful!");
                    return;
                } catch (InvalidInputException e) {
                    actionScreen(input, user, action);
                }
            }
        }
    }
}
