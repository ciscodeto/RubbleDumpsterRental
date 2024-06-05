package model.useCases.rental;

import model.Notification;
import model.Validator;
import model.entities.*;
import model.useCases.client.ClientInsertValidator;
import model.useCases.client.FindClientUseCase;
import model.useCases.client.UpdateClientUseCase;
import model.useCases.rubbleDumpster.FindRubbleDumpsterUseCase;
import model.useCases.rubbleDumpster.UpdateRubbleDumpsterRentalPriceUseCase;
import persistence.dao.ClientDAO;
import persistence.dao.RentalDAO;
import persistence.dao.RubbleDumpsterDAO;
import persistence.utils.EntityNotFoundException;

import java.time.LocalDate;

public class InsertRentalUseCase {
    private RentalDAO rentalDAO;
    private ClientDAO clientDAO;
    private RubbleDumpsterDAO rubbleDumpsterDAO;
    private FindRubbleDumpsterUseCase findRubbleDumpsterUseCase;
    private FindClientUseCase findClientUseCase;

    public InsertRentalUseCase(RentalDAO rentalDAO,
                               FindRubbleDumpsterUseCase findRubbleDumpsterUseCase,
                               FindClientUseCase findClientUseCase) {

        this.rentalDAO = rentalDAO;
        this.findRubbleDumpsterUseCase = findRubbleDumpsterUseCase;
        this.findClientUseCase = findClientUseCase;
    }

    public Rental insertRental(Integer clientId) {
        if (clientId == null) {
            throw new IllegalArgumentException("Client ID is null.");
        }
        Client client = findClientUseCase.findOne(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Client with id " + clientId));

        RubbleDumpster rubbleDumpster = findRubbleDumpsterUseCase.findAll(RubbleDumpsterStatus.AVAILABLE).getFirst();
        Rental rental = new Rental(rubbleDumpster, client, LocalDate.now());
        rental.setRentalStatus(RentalStatus.OPEN);

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
