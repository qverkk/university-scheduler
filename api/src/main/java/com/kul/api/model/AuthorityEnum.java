package com.kul.api.model;

public enum AuthorityEnum {
    ADMIN,
    DZIEKANAT,
    PROWADZACY;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
