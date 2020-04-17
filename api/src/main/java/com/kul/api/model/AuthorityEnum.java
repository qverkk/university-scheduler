package com.kul.api.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AuthorityEnum {
    ADMIN("Admin"),
    DZIEKANAT("Dziekanat"),
    PROWADZACY("Prowadzący");

    public final String displayName;
}
