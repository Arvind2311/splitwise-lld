package com.github.splitwise.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Split {
    private User user;
    private double amount;

    public Split(User user) {
        this.user = user;
    }
}
