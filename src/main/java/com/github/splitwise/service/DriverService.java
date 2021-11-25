package com.github.splitwise.service;

import com.github.splitwise.datastore.ExpenseData;
import com.github.splitwise.datastore.UserData;
import com.github.splitwise.model.ExpenseType;
import com.github.splitwise.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DriverService {

    private ExpenseService es;
    private UserData userData;
    private ExpenseData expenseData;

    public DriverService(UserData userData, ExpenseData expenseData) {
        this.userData = userData;
        this.expenseData = expenseData;
        this.es = new ExpenseService(expenseData);
    }

    public void addUser(String userId, String name, String email, String phoneNumber){
        userData.getGetUserById().put(userId, new User(userId, name, email, phoneNumber));
    }

    public void addExpense(String userId, double amount, List<String> userIds,
                           ExpenseType expenseType, List<Double> splits){
        es.addExpense(userId, amount, userIds, expenseType, splits);
    }

    public String getAllBalances(){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Map<String, Double>> entrySet: expenseData.getGetBalanceMap().entrySet()){
            String user1 = entrySet.getKey();
            for(Map.Entry<String, Double> innerEntrySet: entrySet.getValue().entrySet()){
                String user2 = innerEntrySet.getKey();
                double amount = innerEntrySet.getValue();
                if(amount>0){
                    sb.append(user2+" owes " + user1 + " :" + amount).append("\n");
                }
            }
        }
        if(sb.length()==0)
            sb.append("No Expenses found");
        return sb.toString();
    }

    public String getBalanceForUserId(String userId){
        if(!expenseData.getGetBalanceMap().containsKey(userId)){
            return "No Expenses found for " + userId;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Expenses").append("\n");
        for(Map.Entry<String, Map<String, Double>> entrySet: expenseData.getGetBalanceMap().entrySet()){
            if(entrySet.getKey().equals(userId))
                continue;
            String user1 = entrySet.getKey();
            double amount = entrySet.getValue().getOrDefault(userId, 0.0);
            if(amount>0){
                sb.append(userId+" owes " + user1 + " :" + amount).append("\n");
            }
        }
        sb.append("Balances").append("\n");
        for(Map.Entry<String, Double> entrySet: expenseData.getGetBalanceMap().get(userId).entrySet()){
            String user2 = entrySet.getKey();
            double amount = entrySet.getValue();
            if(amount>0){
                sb.append(user2+" owes " + userId + " :" + amount).append("\n");
            }
        }
        return sb.toString();
    }
}
