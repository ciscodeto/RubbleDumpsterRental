package com.view.rubbledumpsterrental.model.useCases.rental;

import com.view.rubbledumpsterrental.model.useCases.client.FindClientUseCase;
import com.view.rubbledumpsterrental.model.useCases.rubbleDumpster.FindRubbleDumpsterUseCase;
import com.view.rubbledumpsterrental.persistence.dao.RentalDAO;

public class InsertRentalUseCase {
    private RentalDAO rentalDAO;
    private FindClientUseCase findClientUseCase;
    private FindRubbleDumpsterUseCase findBookUseCase;

    public InsertRentalUseCase(RentalDAO rentalDAO) {
        this.rentalDAO = rentalDAO;
    }
}
