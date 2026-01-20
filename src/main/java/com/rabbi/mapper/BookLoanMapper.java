package com.rabbi.mapper;

import com.rabbi.model.BookLoan;
import com.rabbi.payload.dto.BookLoanDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class BookLoanMapper {
    public BookLoanDTO toDTO(BookLoan bookLoan) {
        if(bookLoan == null) return null;
        BookLoanDTO dto = new BookLoanDTO();
        dto.setId(bookLoan.getId());

        if(bookLoan.getUser() != null) {
            dto.setUserId(bookLoan.getUser().getId());
            dto.setUserName(bookLoan.getUser().getFullName());
            dto.setUserEmail(bookLoan.getUser().getEmail());
        }

        if(bookLoan.getBook() != null) {
            dto.setBookId(bookLoan.getBook().getId());
            dto.setBookTitle(bookLoan.getBook().getTitle());
            dto.setBookIsbn(bookLoan.getBook().getIsbn());
            dto.setBookAuthor(bookLoan.getBook().getAuthor());
            dto.setBookCoverImage(bookLoan.getBook().getCoverImageUrl());
        }
        // Book loan details
        dto.setBookLoanType(bookLoan.getType());
        dto.setStatus(bookLoan.getStatus());
        dto.setCheckoutDate(bookLoan.getCheckoutDate());
        dto.setDueDate(bookLoan.getDueDate());
        dto.setRemainingDays(
                ChronoUnit.DAYS.between(
                        LocalDate.now(),
                        bookLoan.getDueDate()
                )
        );
        dto.setReturnDate(bookLoan.getReturnDate());
        dto.setRenewalCount(bookLoan.getRenewalCount());
        dto.setMaxRenewals(bookLoan.getMaxRenewals());

        dto.setNotes(bookLoan.getNotes());
        dto.setIsOverDue(bookLoan.getIsOverDue());
        dto.setOverdueDays(bookLoan.getOverdueDays());
        dto.setCreatedAt(bookLoan.getCreateAt());
        dto.setUpdatedAt(bookLoan.getUpdatedAt());

        return dto;
    }
}
