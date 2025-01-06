package com.dionext.hiki.dto;

import java.time.LocalDateTime;

public class ClaimItem
{
    private String qualifierPropId;

    public String getQualifierPropId() {
        return qualifierPropId;
    }

    public void setQualifierPropId(String qualifierPropId) {
        this.qualifierPropId = qualifierPropId;
    }

    private String value;
    public final String getValue()
    {
        return value;
    }
    public final void setValue(String value)
    {
        this.value = value;
    }
    private String value1;
    public final String getValue1()
    {
        return value1;
    }
    public final void setValue1(String value)
    {
        this.value1 = value;
    }
    private String type;
    public final String getType()
    {
        return type;
    }
    public final void setType(String value)
    {
        this.type = value;
    }
    private boolean isPrefered;
    public final boolean isPrefered()
    {
        return isPrefered;
    }
    public final void setPrefered(boolean value)
    {
        this.isPrefered = value;
    }
    private LocalDateTime startTime = LocalDateTime.MIN;
    public final LocalDateTime getStartTime()
    {
        return startTime;
    }
    public final void setStartTime(LocalDateTime value)
    {
        this.startTime = value;
    }
    private LocalDateTime endTime = LocalDateTime.MIN;
    public final LocalDateTime getEndTime()
    {
        return endTime;
    }
    public final void setEndTime(LocalDateTime value)
    {
        this.endTime = value;
    }
    private LocalDateTime middleTime = LocalDateTime.MIN;
    public final LocalDateTime getMiddleTime()
    {
        return middleTime;
    }
    public final void setMiddleTime(LocalDateTime value)
    {
        this.middleTime = value;
    }
    public final LocalDateTime getControlTime()
    {
        if (!getMiddleTime().equals(LocalDateTime.MIN))
        {
            return getMiddleTime();
        }
        else if (!getStartTime().equals(LocalDateTime.MIN))
        {
            return getStartTime();
        }
        else if (!getEndTime().equals(LocalDateTime.MIN))
        {
            return getEndTime();
        }
        return LocalDateTime.MAX; //for sort null last
    }
    //public bool Replaced { get; set; }
}
