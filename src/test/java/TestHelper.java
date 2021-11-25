import com.github.splitwise.datastore.ExpenseData;
import com.github.splitwise.datastore.UserData;
import com.github.splitwise.model.ExpenseType;
import com.github.splitwise.service.DriverService;
import com.github.splitwise.service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class TestHelper {

    private DriverService driverService;
    private UserData userData;
    private ExpenseData expenseData;

    @BeforeEach
    void setup(){
        userData = new UserData();
        expenseData = new ExpenseData();
        driverService = new DriverService(userData, expenseData);
        driverService.addUser("u1", "user1", "user1@abc.com", "123");
        driverService.addUser("u2", "user2", "user2@abc.com", "456");
        driverService.addUser("u3", "user3", "user3@abc.com", "789");
        driverService.addUser("u4", "user4", "user4@abc.com", "135");
    }

    @Test
    void testFunction(){
//        System.out.println(driverService.getAllBalances());
//        System.out.println(driverService.getBalanceForUserId("u1"));
//        driverService.addExpense("u1", 1000, Arrays.asList("u1","u2","u3","u4"), ExpenseType.EQUAL, new ArrayList<>());
//        System.out.println(driverService.getBalanceForUserId("u4"));
//        System.out.println(driverService.getBalanceForUserId("u1"));
//        driverService.addExpense("u1", 1250, Arrays.asList("u2", "u3"), ExpenseType.EXACT, Arrays.asList(370.0, 880.0));
//        System.out.println(driverService.getAllBalances());
//        driverService.addExpense("u4", 1200, Arrays.asList("u1","u2","u3","u4"), ExpenseType.PERCENT, Arrays.asList(40.0,20.0,20.0,20.0));
//        System.out.println(driverService.getBalanceForUserId("u1"));
//        System.out.println(driverService.getAllBalances());
        driverService.addExpense("u1", 1528.132, Arrays.asList("u2","u3"),
                ExpenseType.PERCENT, Arrays.asList(45.4, 54.6));
        System.out.println(driverService.getAllBalances());
//        System.out.println(driverService.getBalanceForUserId("u1"));
    }
}
