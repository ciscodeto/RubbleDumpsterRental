package model.useCases.rental;

import model.useCases.client.FindClientUseCase;
import model.useCases.client.UpdateClientUseCase;
import model.useCases.rubbleDumpster.FindRubbleDumpsterUseCase;
import model.useCases.rubbleDumpster.UpdateRubbleDumpsterRentalPriceUseCase;
import persistence.dao.ClientDAO;
import persistence.dao.RentalDAO;
import persistence.dao.RubbleDumpsterDAO;

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
}
