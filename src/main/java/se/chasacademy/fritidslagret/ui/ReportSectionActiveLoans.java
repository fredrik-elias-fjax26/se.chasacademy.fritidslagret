package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.model.loan.Loan;
import se.chasacademy.fritidslagret.service.ReportService;

import java.util.ArrayList;
import java.util.List;

public class ReportSectionActiveLoans implements ReportMenuSection {
    private final ReportService reportService;

    public ReportSectionActiveLoans(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public String getTitle() {
        return "Aktiva lån";
    }

    @Override
    public List<String> getRows() {
        List<String> rows = new ArrayList<>();
        for (Loan loan : reportService.getActiveLoans()) {
            rows.add(loan.toString() + ", Försenad: " + reportService.isDelayed(loan));
        }
        return rows;
    }
}
