package com.dumpRents.model.useCases.report;

import com.dumpRents.model.entities.Rental;
import com.dumpRents.model.entities.Report;
import com.dumpRents.persistence.dao.RentalDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class EntryExitReportUseCase {
    private final RentalDAO rentalDAO;

    public EntryExitReportUseCase(RentalDAO rentalDAO) {
        this.rentalDAO = rentalDAO;
    }

    public EntryExitReport generateReport(LocalDate initialDate, LocalDate endDate) {
        if (initialDate == null || endDate == null || initialDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Invalid date range.");
        }

        List<Rental> rentals = rentalDAO.findRentalByPeriod(initialDate, endDate);

        List<Report> reports = rentals.stream()
                .map(rental -> new Report(
                        rental.getRubbleDumpster().getSerialNumber().toString(),
                        rental.getClient().getName(),
                        rental.getInitialDate(),
                        rental.getWithdrawalDate(),
                        null))
                .collect(Collectors.toList());
        return new EntryExitReport(reports);
    }
    public static record EntryExitReport(List<Report> reports) {
    }
}
