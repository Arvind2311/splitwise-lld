package com.github.splitwise.datastore;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class ExpenseData {
    private Map<String, Map<String, Double>> getBalanceMap = new HashMap<>();
}
