package model.useCases.Rental;

import model.Validator;
import model.entities.Rental;
import persistence.dao.DAORental;

public class UCCreateRental {
    DAORental daoRental;

    public UCCreateRental(DAORental daoRental) {
        this.daoRental = daoRental;
    }

    public Integer insert(Rental rental) {
        Validator<>
        return daoRental.create(rental);
    }
}
