package com.rabbi.repo;

import com.rabbi.model.Payment;
import com.rabbi.payload.dto.PaymentDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
