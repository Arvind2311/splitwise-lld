package com.github.splitwise.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class User {
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
}
