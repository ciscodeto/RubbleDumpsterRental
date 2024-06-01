package com.view.rubbledumpsterrental.model.useCases.rental;

import com.view.rubbledumpsterrental.model.entities.Client;
import com.view.rubbledumpsterrental.model.entities.Rental;
import com.view.rubbledumpsterrental.model.entities.RubbleDumpster;
import com.view.rubbledumpsterrental.model.entities.RubbleDumpsterStatus;
import com.view.rubbledumpsterrental.model.useCases.client.FindClientUseCase;
import com.view.rubbledumpsterrental.model.useCases.client.UpdateClientUseCase;
import com.view.rubbledumpsterrental.model.useCases.exceptions.RentalNotAllowedException;
import com.view.rubbledumpsterrental.model.useCases.rubbleDumpster.FindRubbleDumpsterUseCase;
import com.view.rubbledumpsterrental.model.useCases.rubbleDumpster.UpdateRubbleDumpsterRentalPriceUseCase;
import com.view.rubbledumpsterrental.persistence.dao.ClientDAO;
import com.view.rubbledumpsterrental.persistence.dao.RentalDAO;
import com.view.rubbledumpsterrental.persistence.dao.RubbleDumpsterDAO;
import com.view.rubbledumpsterrental.persistence.utils.EntityNotFoundException;

import java.awt.print.Book;

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

    public Rental insertRental(Integer clientId, Integer rubbleDumpsterId) {
        if (clientId == null || rubbleDumpsterId == null) {
            throw new IllegalArgumentException("Client ID and / or Dumpster ID are / is null.");
        }
        RubbleDumpster rubbleDumpster = rubbleDumpsterDAO.findOne(RubbleDumpsterStatus.AVAILABLE.ordinal())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Rubble Dumpster with id " + rubbleDumpsterId));

        Client client = findClientUseCase.findOne(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Client with id " + clientId));
        if (rubbleDumpster.getStatus() == RubbleDumpsterStatus.RENTED) {
            throw new RentalNotAllowedException("Rubble Dumpster is already rented");
        }

        Rental rental = new Rental(rubbleDumpster, client);
        Integer rentalId = rentalDAO.create(rental);

        rubbleDumpster.setStatus(RubbleDumpsterStatus.RENTED);
        rubbleDumpsterDAO.update(rubbleDumpster);

        return rentalDAO.findOne(rentalId).get();
    }
}
