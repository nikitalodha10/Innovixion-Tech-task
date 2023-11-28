import java.util.Scanner;

public class CommandLineCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter an operation (+, -, *, /) or 'exit' to quit:");
            String operation = scanner.nextLine();

            if (operation.equalsIgnoreCase("exit")) {
                System.out.println("Calculator exiting. Goodbye!");
                break;
            }

            if (!isValidOperation(operation)) {
                System.out.println("Invalid operation. Please enter a valid operation.");
                continue;
            }

            System.out.println("Enter the first number:");
            double num1 = getValidNumber(scanner);

            System.out.println("Enter the second number:");
            double num2 = getValidNumber(scanner);

            double result = performOperation(num1, num2, operation);
            System.out.println("Result: " + result);
        }

        scanner.close();
    }

    private static boolean isValidOperation(String operation) {
        return operation.equals("+") || operation.equals("-") || operation.equals("*") || operation.equals("/");
    }

    private static double getValidNumber(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number:");
            scanner.next(); // Consume the invalid input
        }
        return scanner.nextDouble();
    }
    private static double performOperation(double num1, double num2, String operation) {
        switch (operation) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    System.out.println("Error: Cannot divide by zero.");
                    return Double.NaN; // Not a Number
                } else {
                    return num1 / num2;
                }
            default:
                System.out.println("Invalid operation. Returning 0.0");
                return 0.0;
        }
    }
}
