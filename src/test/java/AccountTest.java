import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    Account acc;

    @BeforeEach
    public void initialisation() {
        acc = new Account();
    }

    @Test
    // Условие 2:
    // @DisplayName("Сумма на счете не должно быть меньше отрицательного кредитного максимума.")
    public void currentSumShouldNotBeLessNegCreditMax() {
        final int sum = 1000;
        int balance = acc.getBalance(); // Init balance = 0.
        acc.unblock();
        assertTrue( acc.withdraw(sum)); // Снимаем 1000р и проверяем, что операция удалась.
        assertEquals(balance - sum, acc.getBalance()); // Сравниваем изначальный баланс с балансом после операции.
    }

    @Test
    public  void currentSumShouldBeUnchangeableForBigArgs() {
        int balance = acc.getBalance();
        acc.unblock();
        assertFalse(acc.withdraw(1001));
        assertEquals(balance, acc.getBalance());
    }

    // Условие 3:
    @Test
    public void currentCreditShouldNotBeChangedForUnblockedState() {
        int maxCredit = acc.getMaxCredit();
        acc.unblock();
        assertFalse(acc.setMaxCredit(100));
        assertEquals(maxCredit, acc.getMaxCredit());
    }

    // Условие 4:
    @Test
    public void currentCreditShouldNotBeMoreThanBound() {
        int border = 1000000;
        int maxCredit = acc.getMaxCredit();
        acc.block();
        assertFalse(acc.setMaxCredit(border + 1));
        assertEquals(maxCredit, acc.getMaxCredit());
        assertFalse(acc.setMaxCredit(-border - 1));
        assertEquals(maxCredit, acc.getMaxCredit());
    }

    // Условие 5:
    @Test
    public void currentCreditCanBeChangedForBlockedState() {
        int sum = 100000;
        acc.block();
        assertTrue(acc.setMaxCredit(sum));
    }

    // Условие 6:
    @Test
    public void unBlockMethodShouldBeTrueIfSecondConditionIsDone() {
        int sum = 100;
        assertTrue(acc.deposit(sum));
        acc.block();
        assertTrue(acc.unblock());
    }

    @Test
    public void unBlockMethodShouldBeFalseIfSecondConditionIsNotDone() {
        int withdrawSum = 1000;
        int maxCreditSum = 500;
        assertTrue(acc.withdraw(withdrawSum));
        acc.block();
        assertTrue(acc.setMaxCredit(maxCreditSum));
        assertFalse(acc.unblock());

    }

    // Условие 7:
    @Test
    public void depositMethodWithNegativeOrMoreThanBoundArgsShouldNotChangeSum() {
        int balance = acc.getBalance();
        int negSum = -100;
        int bigSum = 10000000;
        assertFalse(acc.deposit(negSum));
        assertFalse(acc.deposit(bigSum));
        assertEquals(balance, acc.getBalance());
    }

    @Test
    public void withdrawMethodWithNegativeOrMoreThanBoundArgsShouldNotChangeSum() {
        int balance = acc.getBalance();
        int negSum = -100;
        int bigSum = 10000000;
        assertFalse(acc.withdraw(negSum));
        assertFalse(acc.withdraw(bigSum));
        assertEquals(balance, acc.getBalance());
    }

    // Условие 8:
    @Test
    public void currentSumShouldNotBeChangedByResOfDepositMethodMoreThanBound() {
        int bound = 1000000;
        int secondSum = 10;
        int balance = acc.balance;
        assertTrue(acc.deposit(bound));
        assertFalse(acc.deposit(secondSum));
        assertEquals(balance, acc.getBalance() - bound);
    }

    @Test
    public void currentSumShouldNotBeChangedByResOfWithdrawMethodMoreThanBound() {
        int firstSum = 500;
        int secondSum = 600;
        int balance = acc.getBalance();
        assertTrue(acc.withdraw(firstSum));
        assertFalse(acc.withdraw(secondSum));
        assertEquals(balance, acc.getBalance() + firstSum);
    }

    // Условие 9:
    @Test
    public void currentSumShouldNotBeChangedByDepositMethodForBLockedState() {
        int sum = 1000;
        int balance = acc.getBalance();
        acc.block();
        assertFalse(acc.deposit(sum));
        assertEquals(balance, acc.getBalance());
    }

    @Test
    public void currentSumShouldNotBeChangedByWithdrawMethodForBLockedState() {
        int sum = 1000;
        int balance = acc.getBalance();
        acc.block();
        assertFalse(acc.withdraw(sum));
        assertEquals(balance, acc.getBalance());
    }
}