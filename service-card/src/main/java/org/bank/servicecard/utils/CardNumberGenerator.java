package org.bank.servicecard.utils;

import java.util.Random;

public class CardNumberGenerator {
    // Генерирует случайную последовательность цифр указанной длины
    private String generateRandomDigits(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        for (int i = 0; i < length; i++) {
            digits[i] = (char) ('0' + random.nextInt(10)); // генерируем случайную цифру от 0 до 9
        }
        return new String(digits);
    }

    // Вычисляет контрольную сумму по алгоритму Луна
    private int calculateLuhnChecksum(String number) {
        int sum = 0;
        boolean doubleDigit = false;
        for (int i = number.length() - 1; i >= 0; i--) { // идём справа налево
            int digit = Character.getNumericValue(number.charAt(i));
            if (doubleDigit) {
                digit *= 2;
                if (digit > 9) digit -= 9; // если удвоенная цифра больше 9, уменьшаем её
            }
            sum += digit;
            doubleDigit = !doubleDigit; // чередуем умножение каждые две цифры
        }
        return sum % 10 == 0 ? 0 : 10 - (sum % 10); // возвращаем контрольную цифру
    }

    // Создаем полный банковский номер карты
    public String generateBankCardNumber(String issuerPrefix) {
        // Префикс определяется платёжной системой (например, "4" для Visa, "5" для Mastercard)
        String baseNumber = issuerPrefix + generateRandomDigits(15 - issuerPrefix.length());
        int checksum = calculateLuhnChecksum(baseNumber);
        return baseNumber + checksum;
    }
}
