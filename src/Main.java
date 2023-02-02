import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.print("Введите выражение: ");
            Scanner scan = new Scanner(System.in);
            String expression = scan.nextLine();
            Pattern pattern = Pattern.compile("^([0-9]|10|I{1,3}|I?VI{0,3}|I?X)\\s?([+-/*])\\s?([0-9]|10|I{1,3}|I?VI{0,3}|I?X)$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(expression);
            if (matcher.matches()) {
                System.out.println(calc(matcher.group(1), matcher.group(3), matcher.group(2)));
            } else {
                throw new Exception("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Выполняет указанную операцию над двумя операндами
     *
     * @param a        первый операнд
     * @param b        второй операнд
     * @param operator операция
     * @return String Результат математической операции
     */
    private static String calc(String a, String b, String operator) {
        Pattern arabictPattern = Pattern.compile("-?\\d+");
        Pattern romanPattern = Pattern.compile("-?\\w+");
        String result = "";
        try {
            if (arabictPattern.matcher(a).matches() &&
                    arabictPattern.matcher(b).matches()) {
                result = String.valueOf(calcArabian(Integer.parseInt(a), Integer.parseInt(b), operator));
            } else if (romanPattern.matcher(a).matches() &&
                    romanPattern.matcher(b).matches()) {
                result = calcRoman(a, b, operator);
            } else {
                throw new Exception("Используются одновременно разные системы счисления!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * Выполняет указанную операцию над двумя арабскими числами
     *
     * @param a        первый операнд
     * @param b        второй операнд
     * @param operator операция
     * @return String Результат математической операции
     */
    private static int calcArabian(int a, int b, String operator) {
        int result = 0;
        return switch (operator) {
            case ("+") -> a + b;
            case ("-") -> a - b;
            case ("/") -> a / b;
            case ("*") -> a * b;
            default -> result;
        };
    }

    /**
     * Выполняет указанную операцию над двумя римскими числами
     *
     * @param a        первый операнд
     * @param b        второй операнд
     * @param operator операция
     * @return String Результат математической операции
     */
    private static String calcRoman(String a, String b, String operator) {
        int arabianA = convertRomToArab(a);
        int arabianB = convertRomToArab(b);
        int result = calcArabian(arabianA, arabianB, operator);

        return convertArabToRom(result);
    }

    /**
     * Конверрует римское число в арабское
     *
     * @param number Строка содержащая римское число
     * @return Целое арабское число
     */
    private static int convertRomToArab(String number) {
        if (number.equalsIgnoreCase("IV")) {
            return 4;
        } else if (number.equalsIgnoreCase("IX")) {
            return 9;
        }
        char[] charNumber = number.toUpperCase().toCharArray();
        int result = 0;
        for (char currentChar :
                charNumber) {
            switch (currentChar) {
                case ('I'):
                    result += 1;
                    break;
                case ('V'):
                    result += 5;
                    break;
                case ('X'):
                    result += 10;
            }
        }
        return result;
    }

    /**
     * Конвертирует арабское число в римское
     *
     * @param number Целое число
     * @return Строку с римским числом
     */
    private static String convertArabToRom(int number) {
        String romanNumber = "";
        int units = 0;
        int tens = 0;
        int hundreds = 0;
        try {
            if (number <= 0) {
                throw new Exception("В римской системе нет отрицательных чисел и нуля!");
            } else if (number < 10) {
                units = number;
            } else if (number < 100) {
                units = number % 10;
                tens = number / 10;
            } else {
                return "C";
            }

            if (tens != 0) {
                if (tens < 4) {
                    romanNumber += "X".repeat(tens);
                } else if (tens == 4) {
                    romanNumber += "XL";
                } else if (tens < 9) {
                    romanNumber += "L" + "X".repeat(tens - 5);
                } else {
                    romanNumber += "XC";
                }
            }

            if (units < 4) {
                romanNumber += "I".repeat(units);
            } else if (units == 4) {
                romanNumber += "IV";
            } else if (units < 9) {
                romanNumber += "V" + "I".repeat(units - 5);
            } else {
                romanNumber += "IX";
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return romanNumber;
    }
}
