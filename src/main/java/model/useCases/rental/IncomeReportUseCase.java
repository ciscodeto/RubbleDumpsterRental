package model.useCases.rental;

import model.entities.Rental;
import model.entities.Report;
import persistence.dao.RentalDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class IncomeReportUseCase {
    private final RentalDAO rentalDAO;

    public IncomeReportUseCase(RentalDAO rentalDAO) {
        this.rentalDAO = rentalDAO;
    }

    public IncomeReport generateReport(LocalDate initialDate, LocalDate endDate) {
        if (initialDate == null || endDate == null || initialDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Invalid date range.");
        }

        List<Rental> rentals = rentalDAO.findRentalByPeriod(initialDate, endDate);

        double totalFinalAmount = rentals.stream()
                .mapToDouble(Rental::getFinalAmount)
                .sum();

        List<Report> reports = rentals.stream()
                .map(rental -> new Report(
                        rental.getRubbleDumpster().getSerialNumber().toString(),
                        null,
                        rental.getInitialDate(),
                        rental.getWithdrawalDate(),
                        rental.getFinalAmount()))
                .toList();

        return new IncomeReport(reports, totalFinalAmount);
    }

    public record IncomeReport(List<Report> reports, double totalFinalAmount) {
    }
}
