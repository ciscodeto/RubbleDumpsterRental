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
    private ClientDAO clientDAO;
    private RubbleDumpsterDAO rubbleDumpsterDAO;
    private FindRubbleDumpsterUseCase findRubbleDumpsterUseCase;
    private FindClientUseCase findClientUseCase;
    private UpdateRubbleDumpsterRentalPriceUseCase updateRubbleDumpsterRentalPriceUseCase;
    private UpdateClientUseCase clientUseCase;

    public EndRentalUseCase(RentalDAO rentalDAO,
                               FindRubbleDumpsterUseCase findRubbleDumpsterUseCase,
                               FindClientUseCase findClientUseCase,
                               UpdateRubbleDumpsterRentalPriceUseCase updateRubbleDumpsterRentalPriceUseCase,
                               UpdateClientUseCase clientUseCase) {

        this.rentalDAO = rentalDAO;
        this.findRubbleDumpsterUseCase = findRubbleDumpsterUseCase;
        this.findClientUseCase = findClientUseCase;
        this.updateRubbleDumpsterRentalPriceUseCase = updateRubbleDumpsterRentalPriceUseCase;
        this.clientUseCase = clientUseCase;
    }

    public void endRental(Integer rentalId) {
        if (rentalId == null) {
            throw new IllegalArgumentException("ID is null.");
        }

        Rental rental = rentalDAO.findOne(rentalId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Rental for ID " + rentalId));

        rental.setEndDate(LocalDate.now());
        rental.setRentalStatus(RentalStatus.CLOSED);
        rentalDAO.update(rental);

        Integer serialNumber = rental.getRubbleDumpster().getSerialNumber();
        RubbleDumpster rubbleDumpster = findRubbleDumpsterUseCase.findOne(serialNumber).get();
        rubbleDumpster.setStatus(RubbleDumpsterStatus.AVAILABLE);
        rubbleDumpsterDAO.update(rubbleDumpster);
    }
}
