package model.useCases.Rental;

import model.entities.Rental;
import persistence.dao.RentalDAO;

public class InsertRentalUseCase {
    RentalDAO rentalDAO;

    public InsertRentalUseCase(RentalDAO rentalDAO) {
        this.rentalDAO = rentalDAO;
    }

    public Integer insert(Rental rental) {
        return rentalDAO.create(rental);
    }
}
