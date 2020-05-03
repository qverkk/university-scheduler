package com.kul.api.adapter.admin.management;

import com.kul.api.adapter.admin.external.ErrorResponseException;
import com.kul.api.adapter.admin.external.ManagementEndpointClient;
import com.kul.api.adapter.admin.management.lecturer.preferences.*;
import com.kul.api.domain.admin.management.LecturerPreferences;
import com.kul.api.domain.admin.management.ManagedUser;
import com.kul.api.domain.admin.management.ManagementUserRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

public class ManagementUserRepositoryFacade implements ManagementUserRepository {

    private final ManagementEndpointClient client;

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
    public LecturerPreferences updatePreferences(LecturerPreferences preferences) {
        try {
            LecturerPreferencesResponse response = client.updatePreferences(
                    UpdateLecturerPreferenceMapper.toRequest(preferences)
            );
            return UpdateLecturerPreferenceMapper.fromResponse(response);
        } catch (ErrorResponseException ex) {
            throw new LecutrerPreferenecesUpdateException(ex, FailureCause.findByCode(ex.getErrorResponse().getCode()));
        }
    }

    @Override
    public LecturerPreferences fetchPreferences(Long userId, DayOfWeek day) {
        try {
            FetchLecturerPreferenceResponse response = client.fetchPreferences(userId, day);
            return FetchLecturerPreferenceMapper.fromResponse(userId, response, day);
        } catch (ErrorResponseException ex) {
            throw new LecutrerPreferenecesUpdateException(ex, FailureCause.findByCode(ex.getErrorResponse().getCode()));
        }
    }
}
