package model.useCases.rental;

import model.Notification;
import model.Validator;
import model.entities.Rental;
import model.entities.RentalStatus;
import model.entities.RubbleDumpster;
import model.entities.RubbleDumpsterStatus;
import persistence.dao.RentalDAO;
import persistence.dao.RubbleDumpsterDAO;
import persistence.utils.EntityNotFoundException;

import java.time.LocalDate;

public class WithdrawalRequestUseCase {
    private RentalDAO rentalDAO;
    private RubbleDumpsterDAO rubbleDumpsterDAO;
    private FindRentalUseCase findRentalUseCase;

    public WithdrawalRequestUseCase(RentalDAO rentalDAO,
                                    RubbleDumpsterDAO rubbleDumpsterDAO,
                                    FindRentalUseCase findRentalUseCase) {
        this.rentalDAO = rentalDAO;
        this.rubbleDumpsterDAO = rubbleDumpsterDAO;
        this.findRentalUseCase = findRentalUseCase;
    }

    public void requestWithdrawal(Integer rentalId, LocalDate withdrawalDate) {
        if (rentalId == null) {
            throw new IllegalArgumentException("Rental ID is null.");
        }

        if (withdrawalDate == null || withdrawalDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid withdrawal date.");
        }

        Rental rental = findRentalUseCase.findOne(rentalId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Rental with id " + rentalId));

        rental.setWithdrawalRequestDate(LocalDate.now());

        Validator<Rental> validator = new RentalInsertValidator();
        Notification notification = validator.validate(rental);
        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());
        RubbleDumpster rubbleDumpster = rental.getRubbleDumpster();

        rental.setRentalStatus(RentalStatus.WITHDRAWAL_ORDER);
        rubbleDumpster.setStatus(RubbleDumpsterStatus.WITHDRAWAL_ORDER);

        rental.setWithdrawalDate(withdrawalDate);

        rentalDAO.update(rental);
        rubbleDumpsterDAO.update(rubbleDumpster);
    }
}
