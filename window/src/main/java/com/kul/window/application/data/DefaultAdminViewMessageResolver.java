package com.kul.window.application.data;

public class DefaultAdminViewMessageResolver implements AdminViewMessageResolver {

    @Override
    public String nonExistingLecturer() {
        return "This lecturer doesn't exist in our database";
    }

    @Override
    public String nonExistingPreference() {
        return "Preference for this day doesn't exist. Please add one.";
    }

    @Override
    public String insufficientLecturerPreferences() {
        return "Not enough priviliges to update preferences for this user";
    }

    @Override
    public String alreadyExistingPreference() {
        return "Preference for this day already exists. Please update it.";
    }

    @Override
    public String nonValidPreferences() {
        return "Day must be selected and start/end time must match 00:00, 10:00 etc";
    }

    @Override
    public String success() {
        return "Success!";
    }

    @Override
    public String nonValidTime() {
        return "Start time must be before end time";
    }
}
