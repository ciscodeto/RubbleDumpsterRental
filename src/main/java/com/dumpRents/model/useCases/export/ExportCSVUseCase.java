package com.dumpRents.model.useCases.export;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportCSVUseCase {
    public void export(String fileName, String[] headers, List<String[]> data) {
        try (CSVPrinter printer = new CSVPrinter(new FileWriter(fileName), CSVFormat.DEFAULT.withHeader(headers))) {
            for (String[] record : data) {
                printer.printRecord((Object[]) record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
