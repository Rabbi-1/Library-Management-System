package com.rabbi.services;

import com.rabbi.domain.BookLoanStatus;
import com.rabbi.payload.dto.BookLoanDTO;
import com.rabbi.payload.request.BookLoanSearchRequest;
import com.rabbi.payload.request.CheckinRequest;
import com.rabbi.payload.request.CheckoutRequest;
import com.rabbi.payload.request.RenewalRequest;
import com.rabbi.payload.response.PagesResponse;

public interface BookLoanService {

    BookLoanDTO checkoutBook(CheckoutRequest checkoutRequest) throws Exception;

    BookLoanDTO checkoutBookForUser(Long userId, CheckoutRequest checkoutRequest) throws Exception;

    BookLoanDTO checkinBook(CheckinRequest checkinRequest) throws Exception;

    BookLoanDTO renewCheckout(RenewalRequest renewalRequest) throws Exception;

    PagesResponse<BookLoanDTO> getMyBookLoans(BookLoanStatus status, int page, int size);

    PagesResponse<BookLoanDTO> getBookLoans(BookLoanSearchRequest request);

    int updateOverdueBookLoan();


}
