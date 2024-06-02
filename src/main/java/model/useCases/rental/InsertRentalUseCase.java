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

public class InsertRentalUseCase {
    private RentalDAO rentalDAO;
    private ClientDAO clientDAO;
    private RubbleDumpsterDAO rubbleDumpsterDAO;
    private FindRubbleDumpsterUseCase findRubbleDumpsterUseCase;
    private FindClientUseCase findClientUseCase;

    public InsertRentalUseCase(RentalDAO rentalDAO,
                               FindRubbleDumpsterUseCase findRubbleDumpsterUseCase,
                               FindClientUseCase findClientUseCase,
                               UpdateRubbleDumpsterRentalPriceUseCase updateRubbleDumpsterRentalPriceUseCase,
                               UpdateClientUseCase clientUseCase) {

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

        RubbleDumpster rubbleDumpster = findRubbleDumpsterUseCase.findAvailableUnit();

        Rental rental = new Rental(rubbleDumpster, client, LocalDate.now());
        Integer rentalId = rentalDAO.create(rental);

        rubbleDumpster.setStatus(RubbleDumpsterStatus.RENTED);
        rental.setRentalStatus(RentalStatus.OPEN);
        rubbleDumpsterDAO.update(rubbleDumpster);

        return rentalDAO.findOne(rentalId).get();
    }
}
