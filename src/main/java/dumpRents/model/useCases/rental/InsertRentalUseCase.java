package dumpRents.model.useCases.rental;

import dumpRents.model.Notification;
import dumpRents.model.Validator;
import dumpRents.model.entities.*;
import dumpRents.model.useCases.client.FindClientUseCase;
import dumpRents.model.useCases.rubbleDumpster.FindRubbleDumpsterUseCase;
import dumpRents.persistence.dao.ClientDAO;
import dumpRents.persistence.dao.RentalDAO;
import dumpRents.persistence.dao.RubbleDumpsterDAO;
import dumpRents.persistence.utils.EntityNotFoundException;

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
