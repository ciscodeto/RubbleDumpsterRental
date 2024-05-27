package com.view.rubbledumpsterrental.model.useCases.rental;

import model.useCases.client.FindClientUseCase;
import model.useCases.rubbleDumpster.FindRubbleDumpsterUseCase;
import persistence.dao.RentalDAO;

public class InsertRentalUseCase {
    private RentalDAO rentalDAO;
    private FindClientUseCase findClientUseCase;
    private FindRubbleDumpsterUseCase findBookUseCase;

    public InsertRentalUseCase(RentalDAO rentalDAO) {
        this.rentalDAO = rentalDAO;
    }
}
