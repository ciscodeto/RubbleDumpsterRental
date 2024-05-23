package model.useCases.Rental;

import model.entities.Rental;
import persistence.dao.RentalDAO;

public class CreateRentalUseCase {
    RentalDAO rentalDAO;

    public CreateRentalUseCase(RentalDAO rentalDAO) {
        this.rentalDAO = rentalDAO;
    }

    public Integer insert(Rental rental) {
        return rentalDAO.create(rental);
    }
}
