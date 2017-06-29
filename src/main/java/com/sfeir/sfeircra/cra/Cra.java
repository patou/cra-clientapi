package com.sfeir.sfeircra.cra;

import java.io.Serializable;
import java.util.*;

public class Cra implements Serializable {
    private String token;
    private String month;

    private Map<String, Integer> availableMonth;
    private Map<String, Integer> availableActivities;
    private List<Activity> activities;
    private double nbJoursEcart;
    public static final String EMPTY_ACTIVITY_LABEL = "--- choisir une activité ---";

    public Cra() {
        this.activities = new ArrayList<>();
    }

    public Cra(String token, String month) {
        this();
        this.token = token;
        this.month = month;
    }

    public Cra(String token, String month, Map<String, Integer> availableMonth, Map<String, Integer> availableActivities) {
        this(token, month);
        this.availableActivities = availableActivities;
        this.availableMonth = availableMonth;
        this.activities = new ArrayList<>();
    }

    public Map<String, Integer> getAvailableActivities() {
        return availableActivities;
    }

    public Map<String, Integer> getAvailableMonth() {
        return availableMonth;
    }


    public String getToken() {
        return token;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public Activity newActivity(String otpCode) {
        Activity activity = new Activity(otpCode);
        activities.add(activity);
        return activity;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(month);
        builder.append("\t");

        for (Activity activity : activities) {
            builder.append("\n");
            builder.append(activity);
        }
        return builder.toString();
    }

    public boolean correctMonth(String month) {
        return this.month.equals(month);
    }

    public String getMonth() {
        return month;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getActivitiesId(String activityName) {
        return getAvailableActivities().get(activityName);
    }

    /**
     * Si il n'y a qu'une seule activité avec la clé "", c'est que le cra est vide
     * On insert une entré d'exemple
     */
    public void checkForEmptyActivities() {
        if(activities.size() == 1) {
            final Activity firstActivity = activities.get(0);
            if(firstActivity.isLabelEmpty()) {
                activities.remove(0);
                Activity activity = newActivity(EMPTY_ACTIVITY_LABEL);
                activity.setDays(1, 1.0);
                activity.setDays(2, 0.5);
                activity.setComment("Here is an *optional* field to put comment");
            }
        }
    }

    public void setNbJoursEcart(double nbJoursEcart) {
        this.nbJoursEcart = nbJoursEcart;
    }

    public double getNbJoursEcart() {
        return nbJoursEcart;
    }


    public String getLabelForActivityAtIndex(int index) {
        final Set<String> activitiesLabelsSet = getAvailableActivities().keySet();
        final Object[] activitiesLabels = activitiesLabelsSet.toArray();
        String activityLabel = activitiesLabels[index].toString();
        return activityLabel;
    }

    public void setActivitiesIndex(Map<String, Integer> availableActivities) {
        for (Activity activity : activities) {
            Integer activityIndex = availableActivities.get(activity.getOtpCode());
            activity.setActivityIndex(activityIndex);
        }

    }
}