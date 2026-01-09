package com.rabbi.services;

import com.rabbi.model.SubscriptionPlan;
import com.rabbi.payload.dto.SubscriptionPlanDTO;

public interface SubscriptionPlanService {

    SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO planDTO);
}
