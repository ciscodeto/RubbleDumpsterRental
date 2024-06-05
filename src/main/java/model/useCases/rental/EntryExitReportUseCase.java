package model.useCases.rental;

import model.entities.Rental;
import persistence.dao.RentalDAO;

import java.time.LocalDate;
import java.util.List;

public class EntryExitReportUseCase {
    LocalDate initialDate;
    LocalDate endDate;

    public EntryExitReportUseCase(LocalDate initialDate, LocalDate endDate) {
        this.initialDate = initialDate;
        this.endDate = endDate;
    }
}
