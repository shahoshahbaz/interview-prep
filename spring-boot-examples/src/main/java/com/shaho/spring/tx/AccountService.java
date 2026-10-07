package com.shaho.spring.tx;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Transactional rollback rules (classic interview question):
 *  - RuntimeException / Error  -> rollback (default)
 *  - checked Exception         -> COMMIT (default), unless rollbackFor is set
 */
@Service
public class AccountService {

    private final AccountRepository repo;

    public AccountService(AccountRepository repo) {
        this.repo = repo;
    }

    private void move(Long fromId, Long toId, BigDecimal amount) {
        Account from = repo.findById(fromId).orElseThrow();
        Account to = repo.findById(toId).orElseThrow();
        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));
        repo.save(from);
        repo.save(to);
    }

    /** Unchecked exception after the writes -> rolled back. */
    @Transactional
    public void transferRuntimeFail(Long fromId, Long toId, BigDecimal amount) {
        move(fromId, toId, amount);
        throw new IllegalStateException("boom");
    }

    /** Checked exception, default rules -> NOT rolled back (committed). */
    @Transactional
    public void transferCheckedFail(Long fromId, Long toId, BigDecimal amount) throws Exception {
        move(fromId, toId, amount);
        throw new Exception("checked boom");
    }

    /** Checked exception with rollbackFor -> rolled back. */
    @Transactional(rollbackFor = Exception.class)
    public void transferCheckedFailRollbackFor(Long fromId, Long toId, BigDecimal amount) throws Exception {
        move(fromId, toId, amount);
        throw new Exception("checked boom");
    }
}
