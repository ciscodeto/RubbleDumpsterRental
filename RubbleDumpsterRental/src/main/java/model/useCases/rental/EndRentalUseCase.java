package com.model.useCases.rental;

import com.model.entities.*;
import com.model.useCases.client.FindClientUseCase;
import com.model.useCases.client.UpdateClientUseCase;
import com.model.useCases.rubbleDumpster.FindRubbleDumpsterUseCase;
import com.model.useCases.rubbleDumpster.UpdateRubbleDumpsterRentalPriceUseCase;
import com.persistence.dao.ClientDAO;
import com.persistence.dao.RentalDAO;
import com.persistence.dao.RubbleDumpsterDAO;
import com.persistence.utils.EntityNotFoundException;

import java.time.LocalDate;
import java.util.Optional;

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
