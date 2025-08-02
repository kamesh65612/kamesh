package Kamesh3;
public class CalculatorSwitchCase
{
    public static void main(String[] args)
    {
        int A=100;
        int B=30;
        double C;
        char operator = '-';
        switch (operator)
        {
            case '+':
                C=A+B;
                System.out.println("Result: " +C);
                break;
            case '-':
                C=A-B;
                System.out.println("Result: " +C);
                break;
            case '*':
                C=A*B;
                System.out.println("Result: " + C);
                break;
            case '/':
                C=A/B;
                System.out.println("Result: " + C);
                break;
            case '%':
                C=A%B;
                System.out.println("Result: " + C);
                break;
            default:
                System.out.println("Invalid operator. Please select a valid operation.");
        }
    }
}
