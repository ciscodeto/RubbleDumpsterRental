package com.dumpRents.model.useCases.rental;

import com.dumpRents.model.Notification;
import com.dumpRents.model.Validator;
import com.dumpRents.model.entities.*;
import com.dumpRents.model.entities.valueObjects.Address;
import com.dumpRents.model.useCases.client.FindClientUseCase;
import com.dumpRents.model.useCases.rubbleDumpster.FindRubbleDumpsterUseCase;
import com.dumpRents.persistence.dao.ClientDAO;
import com.dumpRents.persistence.dao.RentalDAO;
import com.dumpRents.persistence.dao.RubbleDumpsterDAO;
import com.dumpRents.persistence.utils.EntityNotFoundException;

import java.time.LocalDate;

public class InsertRentalUseCase {
    private RentalDAO rentalDAO;
    private ClientDAO clientDAO;
    private RubbleDumpsterDAO rubbleDumpsterDAO;
    private FindRubbleDumpsterUseCase findRubbleDumpsterUseCase;
    private FindClientUseCase findClientUseCase;

    public InsertRentalUseCase(RentalDAO rentalDAO,
                               FindRubbleDumpsterUseCase findRubbleDumpsterUseCase,
                               FindClientUseCase findClientUseCase,
                               RubbleDumpsterDAO rubbleDumpsterDAO) {

        this.rentalDAO = rentalDAO;
        this.findRubbleDumpsterUseCase = findRubbleDumpsterUseCase;
        this.findClientUseCase = findClientUseCase;
        this.rubbleDumpsterDAO = rubbleDumpsterDAO;
    }

    public Rental insertRental(Integer clientId, Address address) {
        if (clientId == null) {
            throw new IllegalArgumentException("Client ID is null.");
        }
        Client client = findClientUseCase.findOne(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Client with id " + clientId));

        RubbleDumpster rubbleDumpster = findRubbleDumpsterUseCase.findAll(RubbleDumpsterStatus.AVAILABLE).getFirst();
        Rental rental = new Rental(rubbleDumpster, client, LocalDate.now(),address);

        Validator<Rental> validator = new RentalInsertValidator();
        Notification notification = validator.validate(rental);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer rentalId = rentalDAO.create(rental);

        rubbleDumpster.setStatus(RubbleDumpsterStatus.RENTED);
        rubbleDumpsterDAO.update(rubbleDumpster);

        return rentalDAO.findOne(rentalId).get();
    }
}
