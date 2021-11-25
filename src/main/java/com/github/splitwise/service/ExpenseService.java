package com.github.splitwise.service;

import com.github.splitwise.datastore.ExpenseData;
import com.github.splitwise.model.ExpenseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseData expenseData;

    Map<String, Map<String, Double>> balanceMap;

    public ExpenseService(ExpenseData expenseData) {
        this.expenseData = expenseData;
        this.balanceMap = expenseData.getGetBalanceMap();
    }


    public void addExpense(String userId, double amount, List<String> userIds,
                           ExpenseType expenseType, List<Double> splits){
        switch (expenseType){
            case EQUAL:
                addExpenseByEqualSplit(userId, userIds, amount);
                break;
            case EXACT:
                if(isValid(splits, amount) && userIds.size()==splits.size())
                    addExpenseByExactSplit(userId, userIds, splits);
                break;
            case PERCENT:
                if(isValid(splits, 100))
                    addExpenseByPercentSplit(userId, userIds, amount, splits);
                break;
        }
    }

    private boolean isValid(List<Double> splits, double total){
        return splits.stream().mapToDouble(Double::doubleValue).sum()==total;
    }

    private void addExpenseByEqualSplit(String userId, List<String> userIds, double amount){
        double splitAmount = (amount)/userIds.size();
        splitAmount = Math.round(splitAmount*100)/100.0;
        for (String user: userIds){
            amount-=splitAmount;
            if(!balanceMap.containsKey(userId)){
                balanceMap.put(userId, new HashMap<>());
            }
            balanceMap.get(userId).put(user, balanceMap.get(userId).getOrDefault(user, 0.0)+splitAmount);
            if(!balanceMap.containsKey(user)){
                balanceMap.put(user, new HashMap<>());
            }
            balanceMap.get(user).put(userId, balanceMap.get(user).getOrDefault(user, 0.0)+(-1*splitAmount));
        }
        balanceMap.get(userId).put(userIds.get(0), balanceMap.get(userId).get(userIds.get(0))+amount);
        balanceMap.get(userIds.get(0)).put(userId, balanceMap.get(userIds.get(0)).get(userId)-amount);
    }

    private void addExpenseByExactSplit(String userId, List<String> userIds, List<Double> splits){
        for (int i=0;i<splits.size();i++){
            if(!balanceMap.containsKey(userId)){
                balanceMap.put(userId, new HashMap<>());
            }
            balanceMap.get(userId).put(userIds.get(i), balanceMap.get(userId).getOrDefault(userIds.get(i), 0.0)+splits.get(i));
            if(!balanceMap.containsKey(userIds.get(i))){
                balanceMap.put(userIds.get(i), new HashMap<>());
            }
            balanceMap.get(userIds.get(i)).put(userId, balanceMap.get(userIds.get(i)).getOrDefault(userId, 0.0)+(-1*splits.get(i)));
        }
    }

    private void addExpenseByPercentSplit(String userId, List<String> userIds, double amount, List<Double> splits){
        double cumAmount=0.0;
        for (int i=0;i<splits.size();i++){
            double splitAmount = amount*splits.get(i)/100.0;
            splitAmount = Math.round(splitAmount*100)/100.0;
            cumAmount+=splitAmount;
            if(!balanceMap.containsKey(userId)){
                balanceMap.put(userId, new HashMap<>());
            }
            balanceMap.get(userId).put(userIds.get(i), balanceMap.get(userId).getOrDefault(userIds.get(i), 0.0)+splitAmount);
            if(!balanceMap.containsKey(userIds.get(i))){
                balanceMap.put(userIds.get(i), new HashMap<>());
            }
            balanceMap.get(userIds.get(i)).put(userId, balanceMap.get(userIds.get(i)).getOrDefault(userId, 0.0)+(-1*splitAmount));
        }
        amount-=cumAmount;
        amount=Math.round(amount*100)/100.0;
        balanceMap.get(userId).put(userIds.get(0),
                balanceMap.get(userId).get(userIds.get(0))+amount);
        balanceMap.get(userIds.get(0)).put(userId,
                balanceMap.get(userIds.get(0)).get(userId)-amount);
    }

}
