package com.github.splitwise.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EqualSplit extends Split{
    public EqualSplit(User user) {
        super(user);
    }
}
