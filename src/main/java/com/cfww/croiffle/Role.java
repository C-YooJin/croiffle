package com.cfww.croiffle;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    BROKER("ROLE_BROKER"), BUYER("ROLE_BUYER");

    private final String key;

}
