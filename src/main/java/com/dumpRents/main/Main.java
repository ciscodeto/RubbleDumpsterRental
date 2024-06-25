package com.dumpRents.main;

import com.dumpRents.model.entities.*;
import com.dumpRents.model.entities.valueObjects.*;
import com.dumpRents.model.useCases.client.FindClientUseCase;
import com.dumpRents.model.useCases.client.InsertClientUseCase;
import com.dumpRents.model.useCases.client.UpdateClientUseCase;
import com.dumpRents.model.useCases.report.EntryExitReportUseCase;
import com.dumpRents.model.useCases.report.ExportCSVUseCase;
import com.dumpRents.model.useCases.rental.*;
import com.dumpRents.model.useCases.report.IncomeReportUseCase;
import com.dumpRents.model.useCases.rubbleDumpster.*;
import com.dumpRents.persistence.dao.ClientDAO;
import com.dumpRents.persistence.dao.RentalDAO;
import com.dumpRents.persistence.dao.RubbleDumpsterDAO;
import com.dumpRents.persistence.utils.DatabaseBuilder;
import com.dumpRents.repository.InMemoryClientDAO;
import com.dumpRents.repository.InMemoryRentalDAO;
import com.dumpRents.repository.InMemoryRubbleDumpsterDAO;
import com.dumpRents.view.WindowLoader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Main {

    public static ActivateRubbleDumpsterUseCase activateRubbleDumpsterUseCase;
    public static FindRubbleDumpsterUseCase findRubbleDumpsterUseCase;
    public static InactivateRubbleDumpsterUseCase inactivateRubbleDumpsterUseCase;
    public static InsertRubbleDumpsterUseCase insertRubbleDumpsterUseCase;
    public static UpdateRubbleDumpsterRentalPriceUseCase updateRubbleDumpsterRentalPriceUseCase;

    public static FindClientUseCase findClientUseCase;
    public static InsertClientUseCase insertClientUseCase;
    public static UpdateClientUseCase updateClientUseCase;

    public static InsertRentalUseCase insertRentalUseCase;
    public static EndRentalUseCase endRentalUseCase;
    public static FindRentalUseCase findRentalUseCase;
    public static WithdrawalRequestUseCase withdrawalRequestUseCase;

    public static ExportCSVUseCase exportCSVUseCase;
    public static IncomeReportUseCase incomeReportUseCase;
    public static EntryExitReportUseCase entryExitReportUseCase;

    public static void main(String[] args) {
        configureInjection();
        setupDatabase();
        WindowLoader.main(args);
    }

    private static void setupDatabase() {
        DatabaseBuilder dbBuilder = new DatabaseBuilder();
        dbBuilder.buildDatabaseIfMissing();
    }

    private static void configureInjection() {

        RubbleDumpsterDAO rubbleDumpsterDAO = new InMemoryRubbleDumpsterDAO();
        activateRubbleDumpsterUseCase = new ActivateRubbleDumpsterUseCase(rubbleDumpsterDAO);
        findRubbleDumpsterUseCase = new FindRubbleDumpsterUseCase(rubbleDumpsterDAO);
        inactivateRubbleDumpsterUseCase = new InactivateRubbleDumpsterUseCase(rubbleDumpsterDAO);
        insertRubbleDumpsterUseCase = new InsertRubbleDumpsterUseCase(rubbleDumpsterDAO);
        updateRubbleDumpsterRentalPriceUseCase = new UpdateRubbleDumpsterRentalPriceUseCase(rubbleDumpsterDAO);

        ClientDAO clientDAO = new InMemoryClientDAO();
        insertClientUseCase = new InsertClientUseCase(clientDAO);
        findClientUseCase = new FindClientUseCase(clientDAO);
        updateClientUseCase = new UpdateClientUseCase(clientDAO);

        RentalDAO rentalDAO = new InMemoryRentalDAO();
        insertRentalUseCase = new InsertRentalUseCase(rentalDAO,findRubbleDumpsterUseCase,findClientUseCase,rubbleDumpsterDAO);
        findRentalUseCase = new FindRentalUseCase(rentalDAO);
        endRentalUseCase = new EndRentalUseCase(rentalDAO,rubbleDumpsterDAO,findRubbleDumpsterUseCase);
        withdrawalRequestUseCase = new WithdrawalRequestUseCase(rentalDAO,rubbleDumpsterDAO,findRentalUseCase);

        entryExitReportUseCase = new EntryExitReportUseCase(rentalDAO);
        incomeReportUseCase = new IncomeReportUseCase(rentalDAO);
        exportCSVUseCase = new ExportCSVUseCase();
    }
}