package dumpRents.model.useCases.rental;

import dumpRents.model.Notification;
import dumpRents.model.Validator;
import dumpRents.model.entities.Rental;
import dumpRents.model.entities.RentalStatus;
import dumpRents.model.entities.RubbleDumpster;
import dumpRents.model.entities.RubbleDumpsterStatus;
import dumpRents.persistence.dao.RentalDAO;
import dumpRents.persistence.dao.RubbleDumpsterDAO;
import dumpRents.persistence.utils.EntityNotFoundException;

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
