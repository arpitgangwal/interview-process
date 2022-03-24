package com.acme.mytrader.model;

import java.util.Objects;

public final class Security {

    private final String securityId;
    private String securityName;

    public Security(String securityId) {
        this.securityId = securityId;
        this.securityName = securityId;
    }

    public Security(String securityId, String securityName) {
        this.securityId = securityId;
        this.securityName = securityName;
    }

    public String getSecurityId() {
        return securityId;
    }


    public String getSecurityName() {
        return securityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Security)) return false;
        Security security = (Security) o;
        return getSecurityId().equals(security.getSecurityId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSecurityId());
    }
}
