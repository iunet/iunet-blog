package iunet.model.proj;

import java.math.BigDecimal;

public class UmUserToGroup extends UmUserToGroupKey {
    private BigDecimal usageType;

    public BigDecimal getUsageType() {
        return usageType;
    }

    public void setUsageType(BigDecimal usageType) {
        this.usageType = usageType;
    }
}