package com.sfeir.sfeircra.cra;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Activity implements Serializable{

    private String otpCode;
    private Integer activityIndex;

    Map<Integer,Double> days;
    private String comment;

    private String codAct;

    /**
     * Public empty constructor only for gson
     */
    public Activity() {
    }

    public Activity(String otpCode) {
        this.otpCode = otpCode;
        this.days = new HashMap<>();
    }

    public Map<Integer, Double> getDays() {
        return days;
    }

    public void setDays(int index, String val) {
        if(val != null && val.length()>0) {
            setDays(index, Double.parseDouble(val));
        }
    }

    public void setDays(int index, double val) {
        days.put(index, val);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(otpCode);
        builder.append("\n");
        for (int day : days.keySet()) {
            builder.append(day);
            builder.append(":");
            builder.append(days.get(day));
            builder.append("-");
        }

        return builder.toString();
    }

    public String getValueForDay(int i) {
        Double value = days.get(i);
        if(value == null) {
            return "";
        }
        return "" + value;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public boolean isLabelEmpty() {
        return "".equals(otpCode) || Cra.EMPTY_ACTIVITY_LABEL.equals(otpCode);
    }

    public String getOtpCode() {
        return otpCode;
    }

    public boolean otpDontMatch(Activity currentActivity) {
        return !getOtpCode().equals(currentActivity.getOtpCode());
    }

    public void setCodAct(String codAct) {
        this.codAct = codAct;
    }

    public String getCodAct() {
        return codAct;
    }

    public Integer getActivityIndex() {
        return activityIndex;
    }

    public void setActivityIndex(Integer activityIndex) {
        this.activityIndex = activityIndex;
    }
}