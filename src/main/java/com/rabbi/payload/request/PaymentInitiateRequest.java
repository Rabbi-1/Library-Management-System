package com.rabbi.payload.request;

import com.rabbi.domain.PaymentGateway;
import com.rabbi.domain.PaymentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInitiateRequest {
    @NotNull(message = "User ID is mandatory")
    private Long userId;

    private Long bookLoanId;

    @NotNull(message = "Payment type is mandatory")
    private PaymentType paymentType;

    @NotNull(message = "Payment gateway is mandatory")
    private PaymentGateway paymentGateway;

    @NotNull(message = "Amount is mandatory")
    @Positive(message = "Amount must be positive")
    private Long amount;

    @Size(max =500, message = "!= 500")
    private String description;

    private Long fineId;
    private Long subscriptionId;

    @Size(max = 500, message = "Success URL must not exceed 500 char")
    private String successUrl;

    @Size(max = 500, message = "Cancel URL must not exceed 500 char")
    private String cancelUrl;



}
