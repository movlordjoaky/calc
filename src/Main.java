import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        System.out.println(calc(input));
        in.close();
    }

    public static String calc(String input) throws Exception {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new Exception("Количество операторов и операндов может быть равно только трём");
        }
        UserNumber a = new UserNumber(parts[0]);
        UserNumber b = new UserNumber(parts[2]);
        String operator = parts[1];
        if (a.type == UserNumber.Type.INVALID || b.type == UserNumber.Type.INVALID) {
            throw new Exception("Недопустимое число");
        }
        if (a.type != b.type) {
            throw new Exception("Числа разных систем счисления");
        }
        int result = switch (operator) {
            case "+" -> a.value + b.value;
            case "-" -> a.value - b.value;
            case "*" -> a.value * b.value;
            case "/" -> a.value / b.value;
            default -> throw new Exception("Некорректный оператор");
        };
        if (result <= 0 && a.type == UserNumber.Type.ROMAN) {
            throw new Exception("Ответ получился ненатуральным, а в римской системе счисления есть только натуральные числа");
        }
        String notation;
        if (a.type == UserNumber.Type.ROMAN) {
            String[] romanOnesDigits = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
            String[] romanTensDigits = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};
            String onesNotation, tensNotation;
            int ones = result % 10;
            int tens = result / 10;
            onesNotation = romanOnesDigits[ones];
            tensNotation = romanTensDigits[tens];
            notation = tensNotation + onesNotation;
        } else {
            notation = Integer.toString(result);
        }
        return notation;
    }
}

class UserNumber {
    int value;
    Type type;

    enum Type {
        ARABIC, ROMAN, INVALID
    }

    UserNumber(String number) {
        if (number.matches("[1-9]|10")) {
            this.type = Type.ARABIC;
            this.value = Integer.parseInt(number);
            return;
        }
        String[] romanNumbers = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        for (int i = 0; i < romanNumbers.length; i++) {
            if (number.equals(romanNumbers[i])) {
                this.type = Type.ROMAN;
                this.value = i + 1;
                return;
            }
        }
        this.type = Type.INVALID;
    }
}