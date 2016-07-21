package com.tadahtech.enchants.enchant;

/**
 *
 */
public class EnchantLevel {

    public static String getRoman(int level) {
        switch (level) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            default:
                return "NaN";
        }
    }

    public static int getNumber(String roman) {
        switch (roman.toLowerCase()) {
            case "i":
                return 1;
            case "ii":
                return 2;
            case "iii":
                return 3;
            case "iv":
                return 4;
            case "v":
                return 5;
            default:
                return -1;
        }
    }

}
