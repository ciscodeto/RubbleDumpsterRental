package model.entities;

import java.time.LocalDate;

public record Report (String serialNumber, String clientName, LocalDate initialDate, LocalDate withdrawalDate, Double finalAmount){
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RubbleDumpster: ").append(serialNumber);

        if (clientName != null) {
            sb.append(" | Client: ").append(clientName);
        }

        if (initialDate != null) {
            sb.append(" | Initial Date: ").append(initialDate);
        }

        if (withdrawalDate != null) {
            sb.append(" | Withdrawal Date: ").append(withdrawalDate);
        }

        if (finalAmount != null) {
            sb.append(" | Final Amount: ").append(finalAmount);
        }

        return sb.toString();
    }
}