package com.rabbi.payload.dto;

import com.rabbi.domain.PaymentGateway;
import com.rabbi.domain.PaymentStatus;
import com.rabbi.domain.PaymentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
    private Long id;

    @NotNull(message = "User ID is mandatory")
    private Long userId;
    private String userName;
    private String userEmail;
    private Long bookLoanId;
    private Long subscriptionId;
    @NotNull(message = "payment type is mandatory")
    private PaymentType paymentType;

    private PaymentStatus paymentStatus;

    @NotNull(message = "payment gateway is needed")
    private PaymentGateway paymentGateway;

    @NotNull(message = "Amount is mandatory")
    @Positive(message = "Amount must be  positive")
    private Long amount;

    private String transactionId;

    private String gatewayPaymentId;

    private String gatewayOrderId;

    private String gatewaySignature;


    private String description;
    private String failureReason;
    private Integer retryCount;

    private LocalDateTime initiatedAt;

    private LocalDateTime completedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
