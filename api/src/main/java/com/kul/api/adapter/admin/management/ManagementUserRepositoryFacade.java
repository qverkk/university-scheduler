package com.kul.api.adapter.admin.management;

import com.kul.api.adapter.admin.external.ErrorResponseException;
import com.kul.api.adapter.admin.external.ManagementEndpointClient;
import com.kul.api.adapter.admin.management.lecturer.preferences.*;
import com.kul.api.domain.admin.management.LecturerPreferences;
import com.kul.api.domain.admin.management.ManagedUser;
import com.kul.api.domain.admin.management.ManagementUserRepository;
import feign.FeignException;

import java.time.DayOfWeek;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ManagementUserRepositoryFacade implements ManagementUserRepository {

    private final ManagementEndpointClient client;
    private static final Pattern MESSAGE_PATTERN = Pattern.compile("\"message\":\"(?<error>.*)\",");

    public ManagementUserRepositoryFacade(ManagementEndpointClient client) {
        this.client = client;
    }

    @Override
    public List<ManagedUser> getAllUsers() {
        return client.getAllUsers().stream().map(u ->
                new ManagedUser(
                        u.getId(),
                        u.getUsername(),
                        u.getFirstName(),
                        u.getLastName(),
                        u.getEnabled(),
                        u.getAuthority()
                )
        ).collect(Collectors.toList());
    }

    @Override
    public void enableUser(Long id) {
        client.enableUser(id);
    }

    @Override
    public void disableUser(Long id) {
        client.disableUser(id);
    }

    @Override
    public LecturerPreferences updatePreferences(LecturerPreferences preferences) throws Exception {
        try {
            LecturerPreferencesResponse response = client.updatePreferences(preferences);
            return new LecturerPreferences(
                    response.getUserId(),
                    response.getStartTime(),
                    response.getEndTime(),
                    response.getDay()
            );
        }
        catch (ErrorResponseException ex) {
            ex.printStackTrace();
        }
//        catch (FeignException.Forbidden forbidden) {
//            Matcher matcher = MESSAGE_PATTERN.matcher(forbidden.getMessage());
//            if (matcher.find()) {
//                String error = matcher.group("error");
//                if (error.equals("Only admin, dziekanat or user for this permission can update them")) {
//                    throw new InsufficientLecturerPreferencesPriviliges();
//                } else if (error.contains("No username provided")) {
//                    throw new LecturerCannotBeFound();
//                }
//            }
//        } catch (FeignException.UnprocessableEntity unprocessableEntity) {
//            Matcher matcher = MESSAGE_PATTERN.matcher(unprocessableEntity.getMessage());
//            if (matcher.find()) {
//                String error = matcher.group("error");
//                if (error.contains("Preference for ") && error.contains("doesn't exist")) {
//                    throw new LecturerPreferenceDoesntExistException();
//                }
//            }
//        }
        return null;
    }

    @Override
    public LecturerPreferences addPreferences(LecturerPreferences preferences) throws Exception {
        try {
            LecturerPreferencesResponse response = client.addPreferences(preferences);
            return new LecturerPreferences(
                    response.getUserId(),
                    response.getStartTime(),
                    response.getEndTime(),
                    response.getDay()
            );
        } catch (FeignException.Forbidden forbidden) {
            Matcher matcher = MESSAGE_PATTERN.matcher(forbidden.getMessage());
            if (matcher.find()) {
                String error = matcher.group("error");
                if (error.equals("Only admin, dziekanat or user for this permission can update them")) {
                    throw new InsufficientLecturerPreferencesPriviliges();
                } else if (error.contains("No username provided")) {
                    throw new LecturerCannotBeFound();
                }
            }
        } catch (FeignException.UnprocessableEntity unprocessableEntity) {
            Matcher matcher = MESSAGE_PATTERN.matcher(unprocessableEntity.getMessage());
            if (matcher.find()) {
                String error = matcher.group("error");
                if (error.contains("Preference for ") && error.contains("doesn't exist")) {
                    throw new LecturerPreferenceDoesntExistException();
                } else if (error.contains("Preference for ") && error.contains("already exists")) {
                    throw new LecturerPreferenceAlreadyExistsException();
                }
            }
        }
        return null;
    }

    @Override
    public LecturerPreferences fetchPreferences(Long userId, DayOfWeek day) throws Exception {
        try {
            FetchLecturerPreferenceResponse response = client.fetchPreferences(userId, day);
            return new LecturerPreferences(
                    userId,
                    response.getStartTime(),
                    response.getEndTime(),
                    day
            );
        } catch (FeignException.UnprocessableEntity unprocessableEntity) {
            Matcher matcher = MESSAGE_PATTERN.matcher(unprocessableEntity.getMessage());
            if (matcher.find()) {
                String error = matcher.group("error");
                if (error.contains("No username provided")) {
                    throw new LecturerCannotBeFound();
                } else {
                    throw new LecturerPreferenceDoesntExistException();
                }
            }
        }
        return null;
    }
}
