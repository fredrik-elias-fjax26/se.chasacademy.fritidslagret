package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.model.member.Member;
import se.chasacademy.fritidslagret.service.ReportService;

import java.util.ArrayList;
import java.util.List;

public class ReportSectionMembersWithTopThreeMostTotalLoans implements ReportMenuSection {
    private final ReportService reportService;

    public ReportSectionMembersWithTopThreeMostTotalLoans(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public String getTitle() {
        return "Medlem/-mar med flest totalt antal lån(Topp 3)";
    }

    @Override
    public List<String> getRows() {
        List<String> rows = new ArrayList<>();
        for (Member member : reportService.getTopThreeMembersWithMostTotalLoans()) {
            rows.add(String.format("Medlems-Id: %d - %s, %d lån",
                    member.getMemberId(), member.getName(), reportService.getLoanCountForMember(member)));
        }
        return rows;
    }
}
