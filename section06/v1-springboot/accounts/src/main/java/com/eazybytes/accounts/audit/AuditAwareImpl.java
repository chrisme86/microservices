package com.eazybytes.accounts.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component("auditAwareImpl") // name can be any name
public class AuditAwareImpl implements AuditorAware<String>
{
    // createdBy() and updatedBy() will use this String
    @Override
    public Optional<String> getCurrentAuditor()
    {
        return Optional.of("ACCOUNTS_MS");
    }
}
