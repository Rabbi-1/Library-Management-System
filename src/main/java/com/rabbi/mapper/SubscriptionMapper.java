package com.rabbi.mapper;

import com.rabbi.exception.SubscriptionException;
import com.rabbi.model.Subscription;
import com.rabbi.model.User;
import com.rabbi.payload.dto.SubscriptionDTO;
import com.rabbi.repo.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class SubscriptionMapper {

    private final UserRespository userRespository;

    public SubscriptionDTO toDTO(Subscription subscription) {
        if(subscription == null) {
            return null;
        }
        SubscriptionDTO dto = new SubscriptionDTO();
        dto.setId(subscription.getId());
        if(subscription.getUser() != null) {
            dto.setUserId(subscription.getUser().getId());
            dto.setUserName(subscription.getUser().getFullName());
            dto.setUserEmail(subscription.getUser().getEmail());
        }

        if(subscription.getPlan() != null) {
            dto.setPlanId(subscription.getPlan().getId());
        }

        dto.setPlanName(subscription.getPlanName());
        dto.setPlanCode(subscription.getPlanCode());
        dto.setPrice(subscription.getPrice());
        dto.setStartDate(subscription.getStartDate());
        dto.setEndDate(subscription.getEndDate());
        dto.setIsActive(subscription.getIsActive());
        dto.setMaxBooksAllowed(subscription.getMaxBooksAllowed());
        dto.setMaxDaysPerBook(subscription.getMaxDaysPerBook());
        dto.setAutoRenew(subscription.getAutoRenew());
        dto.setCancelledAt(subscription.getCancelledDate());
        dto.setCancellationReason(subscription.getCancellationReason());
        dto.setCreatedAt(subscription.getCreatedAt());
        dto.setUpdatedAt(subscription.getUpdatedAt());

        dto.setDaysRemaining(subscription.getDaysRemaining());
        dto.setIsValid(subscription.isValid());
        dto.setIsExpired(subscription.isExpired());
        return dto;
    }

    public Subscription toEntity(SubscriptionDTO dto) throws SubscriptionException {
        if(dto == null) {
            return null;
        }
        Subscription subscription = new Subscription();
        subscription.setId(dto.getId());
        if(dto.getUserId() != null) {
            User user = userRespository.findById(dto.getUserId())
                    .orElseThrow(() -> new SubscriptionException("User not found"));
            subscription.setUser(user);
        }

        subscription.setPlanName(dto.getPlanName());
        subscription.setPlanCode(dto.getPlanCode());
        subscription.setPrice(dto.getPrice());
        subscription.setStartDate(dto.getStartDate());
        subscription.setEndDate(dto.getEndDate());
        subscription.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : false);
        subscription.setMaxBooksAllowed(dto.getMaxBooksAllowed());
        subscription.setMaxDaysPerBook(dto.getMaxDaysPerBook());
        subscription.setAutoRenew(dto.getAutoRenew() != null ? dto.getAutoRenew() : false);
        subscription.setCancelledDate(dto.getCancelledAt());
        subscription.setCancellationReason(dto.getCancellationReason());
        subscription.setNotes(dto.getNotes());
        return subscription;
    }

    public List<SubscriptionDTO> toDTOList(List<Subscription> subscriptions) {
        if(subscriptions == null) {
            return null;
        }
        return subscriptions.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
