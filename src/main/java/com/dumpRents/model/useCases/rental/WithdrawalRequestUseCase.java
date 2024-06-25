package com.dumpRents.model.useCases.rental;

import com.dumpRents.model.Notification;
import com.dumpRents.model.Validator;
import com.dumpRents.model.entities.Rental;
import com.dumpRents.model.entities.RentalStatus;
import com.dumpRents.model.entities.RubbleDumpster;
import com.dumpRents.model.entities.RubbleDumpsterStatus;
import com.dumpRents.persistence.dao.RentalDAO;
import com.dumpRents.persistence.dao.RubbleDumpsterDAO;
import com.dumpRents.persistence.utils.EntityNotFoundException;

import java.time.LocalDate;

public class WithdrawalRequestUseCase {
    private final RentalDAO rentalDAO;
    private final RubbleDumpsterDAO rubbleDumpsterDAO;
    private final FindRentalUseCase findRentalUseCase;

    public WithdrawalRequestUseCase(RentalDAO rentalDAO,
                                    RubbleDumpsterDAO rubbleDumpsterDAO,
                                    FindRentalUseCase findRentalUseCase) {
        this.rentalDAO = rentalDAO;
        this.rubbleDumpsterDAO = rubbleDumpsterDAO;
        this.findRentalUseCase = findRentalUseCase;
    }

    public void requestWithdrawal(Integer rentalId) {
        if (rentalId == null) {
            throw new IllegalArgumentException("Rental ID cannot be null.");
        }

        LocalDate withdrawalDate = LocalDate.now();

        if (withdrawalDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Withdrawal date cannot be in the past.");
        }

        Rental rental = findRentalUseCase.findOne(rentalId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Rental with ID " + rentalId));

        rental.setWithdrawalRequestDate(LocalDate.now());

        Validator<Rental> validator = new RentalInsertValidator();
        Notification notification = validator.validate(rental);
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }

        RubbleDumpster rubbleDumpster = rental.getRubbleDumpster();
        rental.setRentalStatus(RentalStatus.WITHDRAWAL_ORDER);
        rubbleDumpster.setStatus(RubbleDumpsterStatus.WITHDRAWAL_ORDER);
        rental.setWithdrawalDate(withdrawalDate);

        rentalDAO.update(rental);
        rubbleDumpsterDAO.update(rubbleDumpster);
    }
}
