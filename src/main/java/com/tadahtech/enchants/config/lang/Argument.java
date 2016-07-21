package com.tadahtech.enchants.config.lang;

/**
 *
 */
public enum Argument {

    PLAYER("PLAYER"),
    CATEGORY("CATEGORY"),
    LEVEL("LEVEL"),
    ENCHANT("ENCHANT"),
    ITEM("ITEM"),
    MAX_LEVELS("MAXLEVELS"),
    ENCHANT_DESCRIPTION("ENCHANT_DESCRIPTION"),
    TIME("SECONDS");

    private String inText;

    Argument(String inText) {
        this.inText = inText;
    }

    public String getInText() {
        return this.inText;
    }

}
