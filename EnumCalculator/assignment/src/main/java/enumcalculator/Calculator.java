package enumcalculator;

import java.util.Scanner;

/**
 * Simple command line calculator. Simple program for demo purposes.
 *
 * @""      /    
 */
//public class Calculator {
//
//    /**
//     * Program entry. The program asks for input. Enter an operand, followed by the operator, followed by another operand.
//     * e.g. Entering "3 + 5" (make sure that there are spaces between the tokens) results in "3 + 5 = 8"
//     *
//     * @param args command line parameters.
//     */
//    public static void main(String[] args) {
//
//        var supported = String.join(" ", Operator.supportedSymbols());
//        var askForInputText = "Enter input for calculation (supported: " + supported + ", enter <value> <operator> <value>): ";
//        var quitCalculator = false;
//
//        Scanner scanner = new Scanner(System.in, "UTF-8");
//
//        while (!quitCalculator) {
//
//            System.out.print(askForInputText);
//            String line = scanner.nextLine();
//            String[] tokens = line.split("\\s+");
//
//            if ( tokens.length == 3 ) {
//                System.out.println(line + " = " + compute(tokens));
//            } else {
//                System.out.println("Calculator stops when number of tokens not equal to 3");
//                quitCalculator = true;
//            }
//        }
//    }
//
//    /*
//    This little demo program does not care about checking validity of input...
//    */
//    private static int compute(String... tokens) {
//        if (tokens.length < 3) {
//            return 0;
//        }
//        int a = Integer.parseInt(tokens[0]);
//        int b = Integer.parseInt(tokens[2]);
//        String symbol = tokens[1];
//
//        return Operator.get(symbol).compute(a, b);
//    }
//}
