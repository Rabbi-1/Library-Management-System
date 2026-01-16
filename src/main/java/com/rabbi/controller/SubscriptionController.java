package com.rabbi.controller;

import com.rabbi.exception.SubscriptionException;
import com.rabbi.payload.dto.SubscriptionDTO;
import com.rabbi.payload.response.ApiResponse;
import com.rabbi.services.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(
            @RequestBody SubscriptionDTO subscription
    ) throws Exception {
        SubscriptionDTO dto = subscriptionService.subscribe(subscription);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/user/active")
    public ResponseEntity<?> getUsersActiveSubscriptions(
            @RequestParam(required = false) Long userId
    ) throws Exception {

        SubscriptionDTO dto = subscriptionService.getUsersActiveSubscriptions(userId);
        return ResponseEntity.ok().body(dto);
    }
    //Bug located
    @GetMapping("/admin")
    public ResponseEntity<?> getAllSubscriptions() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<SubscriptionDTO> dtoList = subscriptionService.getAllSubscriptions(pageable);
        return ResponseEntity.ok().body(dtoList);
    }
    @GetMapping("/admin/deactivate-expired")
    public ResponseEntity<?> deactivateExpiredSubscriptions() throws SubscriptionException {

        subscriptionService.deactivateSubscription();
        ApiResponse apiResponse = new ApiResponse("task done", true);
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/cancel/{subscriptionId}")
    public ResponseEntity<?> cancelSubscription(
            @PathVariable Long subscriptionId,
            @RequestParam(required = false) String reason) throws SubscriptionException {
        SubscriptionDTO subscription = subscriptionService.cancelSubscription(subscriptionId, reason);
        return ResponseEntity.ok().body(subscription);

    }

    @PostMapping("/activate")
    public ResponseEntity<?> activateSubscription(
            @RequestParam Long subscriptionId,
            @RequestParam Long paymentId) throws SubscriptionException {
        SubscriptionDTO subscription = subscriptionService.activateSubscription(subscriptionId, paymentId);
        return ResponseEntity.ok().body(subscription);
    }







}
