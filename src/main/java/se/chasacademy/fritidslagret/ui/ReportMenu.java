package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.service.ReportService;

import java.util.List;

public class ReportMenu {
    private final List<ReportMenuSection> reportSections;

    public ReportMenu(ReportService reportService) {
        // A new criterion becomes a new class in this list, showReport() stays the same
        this.reportSections = List.of(
                new ReportSectionActiveLoans(reportService),
                new ReportSectionMostBorrowedEquipment(reportService),
                new ReportSectionNeverBorrowedEquipment(reportService),
                new ReportSectionMembersWithTopThreeMostTotalLoans(reportService),
                new ReportSectionAvailableEquipment(reportService)
        );
    }

    void showReport() {
        IO.println("Rapport\n-----------------");
        for (ReportMenuSection section : reportSections) {
            IO.println(section.getTitle() + ":");
            List<String> rows = section.getRows();
            if (rows.isEmpty()) {
                IO.println("\tInget att visa");
            }
            for (String row : rows) {
                IO.println("\t" + row);
            }
            IO.println("");
        }
    }
}
