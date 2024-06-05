package model.useCases.rental;

import model.entities.*;
import model.useCases.client.FindClientUseCase;
import model.useCases.client.UpdateClientUseCase;
import model.useCases.rubbleDumpster.FindRubbleDumpsterUseCase;
import model.useCases.rubbleDumpster.UpdateRubbleDumpsterRentalPriceUseCase;
import persistence.dao.ClientDAO;
import persistence.dao.RentalDAO;
import persistence.dao.RubbleDumpsterDAO;
import persistence.utils.EntityNotFoundException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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

        rental.setEndDate(LocalDate.now());

        Integer serialNumber = rental.getRubbleDumpster().getSerialNumber();
        RubbleDumpster rubbleDumpster = findRubbleDumpsterUseCase.findOne(serialNumber).get();

        rental.setRentalStatus(RentalStatus.CLOSED);
        rubbleDumpster.setStatus(RubbleDumpsterStatus.AVAILABLE);
        rental.setFinalAmount(rental.calculateFinalAmount());

        rentalDAO.update(rental);
        rubbleDumpsterDAO.update(rubbleDumpster);
    }
}
