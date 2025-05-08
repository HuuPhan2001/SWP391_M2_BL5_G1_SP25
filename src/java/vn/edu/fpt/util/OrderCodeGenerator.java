package vn.edu.fpt.util;

import java.util.Random;

public class OrderCodeGenerator {
    private static final String PREFIX = "ORD-";
    private static final int CODE_LENGTH = 8;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random RANDOM = new Random();

    public static String generateOrderCode() {
        StringBuilder code = new StringBuilder(PREFIX);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }
} 