package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.service.ReportService;

import java.util.ArrayList;
import java.util.List;

public class ReportSectionNeverBorrowedEquipment implements ReportMenuSection {
    private final ReportService reportService;

    public ReportSectionNeverBorrowedEquipment(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public String getTitle() {
        return "Utrustning som aldrig lånats ut";
    }

    @Override
    public List<String> getRows() {
        List<String> rows = new ArrayList<>();
        for (Equipment equipment : reportService.getNeverBorrowedEquipment()) {
            rows.add(String.format("Id: %d - %s (%s)",
                    equipment.getEquipmentId(), equipment.getEquipmentName(), equipment.getType()));
        }
        return rows;
    }
}
