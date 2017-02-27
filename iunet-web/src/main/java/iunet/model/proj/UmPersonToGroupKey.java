package iunet.model.proj;

import java.math.BigDecimal;

public class UmPersonToGroupKey {
    private BigDecimal personId;

    private BigDecimal groupId;

    public BigDecimal getPersonId() {
        return personId;
    }

    public void setPersonId(BigDecimal personId) {
        this.personId = personId;
    }

    public BigDecimal getGroupId() {
        return groupId;
    }

    public void setGroupId(BigDecimal groupId) {
        this.groupId = groupId;
    }
}