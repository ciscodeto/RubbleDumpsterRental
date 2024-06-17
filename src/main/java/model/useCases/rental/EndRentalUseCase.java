package model.useCases.rental;

import model.entities.*;
import model.useCases.rubbleDumpster.FindRubbleDumpsterUseCase;
import persistence.dao.RentalDAO;
import persistence.dao.RubbleDumpsterDAO;
import persistence.utils.EntityNotFoundException;

public class EndRentalUseCase {
    private RentalDAO rentalDAO;
    private RubbleDumpsterDAO rubbleDumpsterDAO;
    private FindRubbleDumpsterUseCase findRubbleDumpsterUseCase;

    public EndRentalUseCase(RentalDAO rentalDAO,
                            RubbleDumpsterDAO rubbleDumpsterDAO,
                            FindRubbleDumpsterUseCase findRubbleDumpsterUseCase) {
        this.rentalDAO = rentalDAO;
        this.rubbleDumpsterDAO = rubbleDumpsterDAO;
        this.findRubbleDumpsterUseCase = findRubbleDumpsterUseCase;
    }

    public void endRental(Integer rentalId) {
        if (rentalId == null) {
            throw new IllegalArgumentException("ID is null.");
        }

        Rental rental = rentalDAO.findOne(rentalId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Rental for ID " + rentalId));

        rental.conclude();

        Integer serialNumber = rental.getRubbleDumpster().getSerialNumber();
        RubbleDumpster rubbleDumpster = findRubbleDumpsterUseCase.findOne(serialNumber).get();

        rental.setRentalStatus(RentalStatus.CLOSED);
        rubbleDumpster.setStatus(RubbleDumpsterStatus.AVAILABLE);
        rental.setFinalAmount(rental.calculateFinalAmount());

        rentalDAO.update(rental);
        rubbleDumpsterDAO.update(rubbleDumpster);
    }
}
