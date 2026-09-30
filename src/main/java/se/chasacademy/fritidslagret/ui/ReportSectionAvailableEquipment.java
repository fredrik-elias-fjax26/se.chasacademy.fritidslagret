package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.service.ReportService;

import java.util.ArrayList;
import java.util.List;

public class ReportSectionAvailableEquipment implements ReportMenuSection {
    private final ReportService reportService;

    public ReportSectionAvailableEquipment(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public String getTitle() {
        return "Tillgänglig utrustning";
    }

    @Override
    public List<String> getRows() {
        List<String> rows = new ArrayList<>();
        for (Equipment equipment : reportService.getAvailableEquipment()) {
            rows.add(String.format("Id: %d - %s (%s), max-lånetid %d dagar",
                    equipment.getEquipmentId(), equipment.getEquipmentName(), equipment.getType(),
                    equipment.getMaxLoanDays()));
        }
        return rows;
    }
}
