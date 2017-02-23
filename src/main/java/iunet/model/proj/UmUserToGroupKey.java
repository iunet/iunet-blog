package iunet.model.proj;

import java.math.BigDecimal;

public class UmUserToGroupKey {
    private BigDecimal userId;

    private BigDecimal groupId;

    public BigDecimal getUserId() {
        return userId;
    }

    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    public BigDecimal getGroupId() {
        return groupId;
    }

    public void setGroupId(BigDecimal groupId) {
        this.groupId = groupId;
    }
}