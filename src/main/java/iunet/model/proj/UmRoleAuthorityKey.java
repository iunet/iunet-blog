package iunet.model.proj;

import java.math.BigDecimal;

public class UmRoleAuthorityKey {
    private BigDecimal roleId;

    private BigDecimal functionId;

    public BigDecimal getRoleId() {
        return roleId;
    }

    public void setRoleId(BigDecimal roleId) {
        this.roleId = roleId;
    }

    public BigDecimal getFunctionId() {
        return functionId;
    }

    public void setFunctionId(BigDecimal functionId) {
        this.functionId = functionId;
    }
}