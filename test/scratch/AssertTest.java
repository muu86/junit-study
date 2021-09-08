package scratch;

import org.junit.*;

import java.io.*;
import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class AssertTest {

    class InsufficientFundsException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public InsufficientFundsException(String message) {
            super(message);
        }
    }

    class Account {
        int balance;
        String name;

        public Account(String name) {
            this.name = name;
        }

        void deposit(int dollars) {
            balance += dollars;
        }

        void withdraw(int dollars) {
            if (balance < dollars) {
                throw new InsufficientFundsException("balance only " + balance);
            }
            balance -= dollars;
        }

        public String getName() {
            return name;
        }

        public int getBalance() {
            return balance;
        }

        public boolean hasPositiveBalance() {
            return balance > 0;
        }

    }

    class Customer {
        List<Account> accounts = new ArrayList<>();

        void add(Account account) {
            accounts.add(account);
        }

        Iterator<Account> getAccounts() {
            return accounts.iterator();
        }
    }

    private Account account;

    @Before
    public void createAccount() {
        account = new Account("an account name");
    }

    @Test
    public void checkAccountName() {
        assertThat(account.getName(), startsWith("an "));
    }

    @Test
    public void hasPositiveBalance() {
        account.deposit(50);
        System.out.println(account.getBalance());
        assertTrue(account.hasPositiveBalance());
    }

    @Test
    public void depositIncreaseBalance() {
        int initialBalance = account.getBalance();
        account.deposit(100);
        assertTrue(account.getBalance() > initialBalance);
        assertThat(account.getBalance(), equalTo(100));
    }

    @Test
    public void depositIncreaseBalance_hamcrestAssertTrue() {
        account.deposit(50);
        assertThat(account.getBalance() > 0, is(true));
    }

    @Test
    public void compareArraysPassing() {
        assertThat(new String[]{"a", "b"}, equalTo(new String[]{"a", "b"}));
        assertThat(new String[]{"a", "b"}, not(equalTo(new String[]{"a", "b", "c"})));
    }

    @Test
    public void compareCollectionPassing() {
        assertThat(Arrays.asList(new String[]{"a", "b"}), equalTo(Arrays.asList(new String[]{"a", "b"})));
    }
}
