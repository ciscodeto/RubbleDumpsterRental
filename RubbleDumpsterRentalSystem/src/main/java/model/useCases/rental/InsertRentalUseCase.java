package model.useCases.rental;

import model.entities.Rental;
import persistence.dao.RentalDAO;

public class InsertRentalUseCase {
    private RentalDAO rentalDAO;
    private FindClientUseCase findClientUseCase;
    private FindBookUseCase findBookUseCase;
    private up

    public InsertRentalUseCase(RentalDAO rentalDAO) {
        this.rentalDAO = rentalDAO;
    }
}
