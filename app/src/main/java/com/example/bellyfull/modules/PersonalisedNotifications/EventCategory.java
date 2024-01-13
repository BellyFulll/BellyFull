package com.example.bellyfull.modules.PersonalisedNotifications;

public class EventCategory {
    private String primaryColor;
    private String selectedColor;
    private String iconColor;
    private String eventCategoryName;

    public EventCategory(String primaryColor, String selectedColor, String iconColor, String eventCategoryName) {
        this.primaryColor = primaryColor;
        this.selectedColor = selectedColor;
        this.iconColor = iconColor;
        this.eventCategoryName = eventCategoryName;
    }

    public String getIconColor() {
        return iconColor;
    }

    public void setIconColor(String iconColor) {
        this.iconColor = iconColor;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(String selectedColor) {
        this.selectedColor = selectedColor;
    }

    public String getEventCategoryName() {
        return eventCategoryName;
    }

    public void setEventCategoryName(String eventCategoryName) {
        this.eventCategoryName = eventCategoryName;
    }
}
