package com.shaho.spring.tx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// Deliberately NOT @Transactional on the test, so the service's transaction is the real one.
@SpringBootTest
class AccountServiceTest {

    @Autowired AccountService service;
    @Autowired AccountRepository repo;

    Long aliceId;
    Long bobId;

    @BeforeEach
    void setUp() {
        repo.deleteAll();
        aliceId = repo.save(new Account("alice", new BigDecimal("100.00"))).getId();
        bobId = repo.save(new Account("bob", new BigDecimal("100.00"))).getId();
    }

    private BigDecimal balance(Long id) {
        return repo.findById(id).orElseThrow().getBalance();
    }

    @Test
    void runtimeExceptionRollsBack() {
        assertThrows(IllegalStateException.class,
                () -> service.transferRuntimeFail(aliceId, bobId, new BigDecimal("30.00")));
        assertEquals(0, new BigDecimal("100.00").compareTo(balance(aliceId)));
        assertEquals(0, new BigDecimal("100.00").compareTo(balance(bobId)));
    }

    @Test
    void checkedExceptionCommitsByDefault() {
        assertThrows(Exception.class,
                () -> service.transferCheckedFail(aliceId, bobId, new BigDecimal("30.00")));
        assertEquals(0, new BigDecimal("70.00").compareTo(balance(aliceId)));
        assertEquals(0, new BigDecimal("130.00").compareTo(balance(bobId)));
    }

    @Test
    void checkedExceptionWithRollbackForRollsBack() {
        assertThrows(Exception.class,
                () -> service.transferCheckedFailRollbackFor(aliceId, bobId, new BigDecimal("30.00")));
        assertEquals(0, new BigDecimal("100.00").compareTo(balance(aliceId)));
        assertEquals(0, new BigDecimal("100.00").compareTo(balance(bobId)));
    }
}
