package com.github.splitwise.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExactSplit extends Split {
    public ExactSplit(User user) {
        super(user);
    }
}
