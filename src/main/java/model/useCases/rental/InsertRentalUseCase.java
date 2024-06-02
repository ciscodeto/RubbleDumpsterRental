package com.view.rubbledumpsterrental.model.useCases.rental;

import com.view.rubbledumpsterrental.model.entities.*;
import com.view.rubbledumpsterrental.model.useCases.client.FindClientUseCase;
import com.view.rubbledumpsterrental.model.useCases.client.UpdateClientUseCase;
import com.view.rubbledumpsterrental.model.useCases.rubbleDumpster.FindRubbleDumpsterUseCase;
import com.view.rubbledumpsterrental.model.useCases.rubbleDumpster.UpdateRubbleDumpsterRentalPriceUseCase;
import com.view.rubbledumpsterrental.persistence.dao.ClientDAO;
import com.view.rubbledumpsterrental.persistence.dao.RentalDAO;
import com.view.rubbledumpsterrental.persistence.dao.RubbleDumpsterDAO;
import com.view.rubbledumpsterrental.persistence.utils.EntityNotFoundException;

import java.time.LocalDate;

public class InsertRentalUseCase {
    private RentalDAO rentalDAO;
    private ClientDAO clientDAO;
    private RubbleDumpsterDAO rubbleDumpsterDAO;
    private FindRubbleDumpsterUseCase findRubbleDumpsterUseCase;
    private FindClientUseCase findClientUseCase;
    private UpdateRubbleDumpsterRentalPriceUseCase updateRubbleDumpsterRentalPriceUseCase;
    private UpdateClientUseCase clientUseCase;

    public InsertRentalUseCase(RentalDAO rentalDAO,
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

    public Rental insertRental(Integer clientId) {
        if (clientId == null) {
            throw new IllegalArgumentException("Client ID is null.");
        }
        Client client = findClientUseCase.findOne(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Client with id " + clientId));

        RubbleDumpster rubbleDumpster = rubbleDumpsterDAO.findOne(RubbleDumpsterStatus.AVAILABLE.ordinal())
                .orElseThrow(() -> new EntityNotFoundException("No Rubble Dumpster Available for renting."));

        Rental rental = new Rental(rubbleDumpster, client, LocalDate.now());
        Integer rentalId = rentalDAO.create(rental);

        rubbleDumpster.setStatus(RubbleDumpsterStatus.RENTED);
        rental.setRentalStatus(RentalStatus.OPEN);
        rubbleDumpsterDAO.update(rubbleDumpster);

        return rentalDAO.findOne(rentalId).get();
    }
}
