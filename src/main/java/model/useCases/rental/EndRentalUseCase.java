package com.view.rubbledumpsterrental.model.useCases.rental;

import com.view.rubbledumpsterrental.model.useCases.client.FindClientUseCase;
import com.view.rubbledumpsterrental.model.useCases.client.UpdateClientUseCase;
import com.view.rubbledumpsterrental.model.useCases.rubbleDumpster.FindRubbleDumpsterUseCase;
import com.view.rubbledumpsterrental.model.useCases.rubbleDumpster.UpdateRubbleDumpsterRentalPriceUseCase;
import com.view.rubbledumpsterrental.persistence.dao.ClientDAO;
import com.view.rubbledumpsterrental.persistence.dao.RentalDAO;
import com.view.rubbledumpsterrental.persistence.dao.RubbleDumpsterDAO;

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
