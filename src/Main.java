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
     * @param a первый операнд
     * @param b второй операнд
     * @param operator операция
     * @return String Результат математической операции
     */
    private static String calc(String a, String b, String operator) {
        Pattern arabictPattern = Pattern.compile("-?\\d+");
        Pattern romanianPattern = Pattern.compile("-?\\w+");
        String result = "";
        try {
            if (arabictPattern.matcher(a).matches() &&
                    arabictPattern.matcher(b).matches()) {
                result = String.valueOf(calcArabian(Integer.parseInt(a), Integer.parseInt(b), operator));
            } else if (romanianPattern.matcher(a).matches() &&
                    romanianPattern.matcher(b).matches()) {
//                calcRomanian(a, b, operator);
                System.out.println("under construction");
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
     * @param a первый операнд
     * @param b второй операнд
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

//    private
}