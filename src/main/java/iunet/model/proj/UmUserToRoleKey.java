package iunet.model.proj;

import java.math.BigDecimal;

public class UmUserToRoleKey {
    private BigDecimal roleId;

    private BigDecimal userId;

    public BigDecimal getRoleId() {
        return roleId;
    }

    public void setRoleId(BigDecimal roleId) {
        this.roleId = roleId;
    }

    public BigDecimal getUserId() {
        return userId;
    }

    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }
}