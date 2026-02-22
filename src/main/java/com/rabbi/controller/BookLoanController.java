package com.rabbi.controller;


import com.rabbi.payload.dto.BookLoanDTO;
import com.rabbi.payload.request.CheckinRequest;
import com.rabbi.payload.request.CheckoutRequest;
import com.rabbi.payload.request.RenewalRequest;
import com.rabbi.services.BookLoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book-loans")
public class BookLoanController {
    private final BookLoanService bookLoanService;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkoutBook(@Valid @RequestBody CheckoutRequest checkoutRequest) throws Exception {
        BookLoanDTO bookLoan = bookLoanService.checkoutBook(checkoutRequest);
        return new ResponseEntity<>(bookLoan, HttpStatus.CREATED);
    }
    @PostMapping("/checkout/user/{userId}")
    public ResponseEntity<?> checkoutBookForUser(
            @PathVariable Long userId,
            @Valid @RequestBody CheckoutRequest checkoutRequest) throws Exception {

        BookLoanDTO bookLoan =
                bookLoanService.checkoutBookForUser(userId, checkoutRequest);

        return new ResponseEntity<>(bookLoan, HttpStatus.CREATED);
    }
    @PostMapping("/checkin")
    public ResponseEntity<?> checkin(
            @Valid @RequestBody CheckinRequest checkinRequest) throws Exception {

        BookLoanDTO bookLoan = bookLoanService
                .checkinBook(checkinRequest);

        return new ResponseEntity<>(bookLoan, HttpStatus.OK);
    }
    @PostMapping("/renew")
    public ResponseEntity<?> renew(
            @Valid @RequestBody RenewalRequest renewalRequest) throws Exception {

        BookLoanDTO bookLoan = bookLoanService
                .renewCheckout(renewalRequest);

        return new ResponseEntity<>(bookLoan, HttpStatus.OK);
    }
}
