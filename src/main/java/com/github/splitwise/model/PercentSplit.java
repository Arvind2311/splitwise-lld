package com.github.splitwise.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PercentSplit extends Split {
    public PercentSplit(User user) {
        super(user);
    }
}
