package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.service.ReportService;

import java.util.ArrayList;
import java.util.List;

public class ReportSectionMostBorrowedEquipment implements ReportMenuSection {
    private final ReportService reportService;

    public ReportSectionMostBorrowedEquipment(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public String getTitle() {
        return "Mest utlånad utrustning";
    }

    @Override
    public List<String> getRows() {
        List<String> rows = new ArrayList<>();
        for (Equipment equipment : reportService.getMostBorrowedEquipment()) {
            rows.add(String.format("Id: %d - %s (%s), %d lån",
                    equipment.getEquipmentId(), equipment.getEquipmentName(), equipment.getType(),
                    reportService.getLoanCountForEquipment(equipment)));
        }
        return rows;
    }
}
