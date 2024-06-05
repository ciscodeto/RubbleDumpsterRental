package model.entities;

import java.time.LocalDate;

public record Report (String serialNumber, String clientName, LocalDate initialDate, LocalDate withdrawalDate, Double finalAmount){}