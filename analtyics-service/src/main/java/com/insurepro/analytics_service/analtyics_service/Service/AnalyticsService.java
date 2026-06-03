package com.insurepro.analytics_service.analtyics_service.Service;

import com.insurepro.analytics_service.analtyics_service.Model.AnalyticsResponse;

public interface AnalyticsService {

    AnalyticsResponse getAnalytics(long customerId);
}
