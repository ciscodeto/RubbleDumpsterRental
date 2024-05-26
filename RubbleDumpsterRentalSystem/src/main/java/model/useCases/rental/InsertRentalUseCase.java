package model.useCases.rental;

import model.entities.Rental;
import persistence.dao.RentalDAO;

public class InsertRentalUseCase {
    RentalDAO rentalDAO;
    Rental newRental;

    public InsertRentalUseCase(RentalDAO rentalDAO) {
        this.rentalDAO = rentalDAO;
    }

    public Integer insert(Rental rental) {
        return rentalDAO.create(rental);
    }
}
