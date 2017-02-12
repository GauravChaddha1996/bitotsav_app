package com.bitmesra.bitotsav.features.details;

import com.bitmesra.bitotsav.database.models.events.EventDto;

/**
 * Created by Batdroid on 7/2/17 for Bitotsav.
 */

public interface DetailsViewInterface {
    void showLoading();
    void hideLoading();
    void showError();
    void showAchievment();
    void hideAchievment();
    void errorAchievment();
    void updateDetailView(EventDto eventDto);
}
