package com.dumpRents.model.useCases.rental;

import com.dumpRents.model.entities.Rental;
import com.dumpRents.model.entities.RubbleDumpster;
import com.dumpRents.model.entities.RubbleDumpsterStatus;
import com.dumpRents.model.useCases.rubbleDumpster.FindRubbleDumpsterUseCase;
import com.dumpRents.persistence.dao.RentalDAO;
import com.dumpRents.persistence.dao.RubbleDumpsterDAO;
import com.dumpRents.persistence.utils.EntityNotFoundException;

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

        rental.endRental();

        Integer serialNumber = rental.getRubbleDumpster().getSerialNumber();
        RubbleDumpster rubbleDumpster = findRubbleDumpsterUseCase.findOne(serialNumber).get();

        rubbleDumpster.setStatus(RubbleDumpsterStatus.AVAILABLE);

        rentalDAO.update(rental);
        rubbleDumpsterDAO.update(rubbleDumpster);
    }
}
