package model.useCases.rental;

import model.useCases.client.FindClientUseCase;
import model.useCases.rubbleDumpster.FindRubbleDumpsterUseCase;
import persistence.dao.RentalDAO;

public class InsertRentalUseCase {
    private RentalDAO rentalDAO;
    private FindClientUseCase findClientUseCase;
    private FindRubbleDumpsterUseCase findBookUseCase;
    private up

    public InsertRentalUseCase(RentalDAO rentalDAO) {
        this.rentalDAO = rentalDAO;
    }
}
