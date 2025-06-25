package oi.github.cadnunsdimir.congespapi.domain.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import oi.github.cadnunsdimir.congespapi.entities.territory.assignment.AssignmentRecord;
import oi.github.cadnunsdimir.congespapi.entities.territory.assignment.Sheet;

@Data
@NoArgsConstructor
public class TerritoryAssignmentSheetData {
    private long totalPages;
    private int itensPerPage;
    private int serviceYear;
    private List<TerritoryGroup> numbers;

    public TerritoryAssignmentSheetData(Sheet sheet, List<AssignmentRecord> records, int itensPerPage) {
        this.itensPerPage = itensPerPage;
        this.serviceYear = sheet.getServiceYear();
        Map<Integer, List<AssignmentRecord>> data =  records.stream().collect(Collectors.groupingBy(x-> x.getTerritoryNumber().getNumber()));
        this.numbers = TerritoryGroup.map(data);
        this.totalPages  = this.calculateTotalSheetNumber();
    }

    private long calculateTotalSheetNumber() {
        var maxRecordsInAGroup = numbers.stream()
            .map(x-> x.getRecords().size())
            .max(Comparator.naturalOrder())
            .orElse(0);

        if (maxRecordsInAGroup == 0) {
            return 1;
        }

        int pages = maxRecordsInAGroup / itensPerPage;

        if (maxRecordsInAGroup % itensPerPage > 0) {
            pages++;
        }

        return pages;
    }
    
    @Data
    @NoArgsConstructor
    public static class TerritoryGroup {
        private int number;
        private LocalDate lastDate;
        private List<TerritoryGroupRecord> records;
        public static List<TerritoryGroup> map(Map<Integer, List<AssignmentRecord>> data) {
            List<TerritoryGroup> list = new ArrayList<>();

            for (Map.Entry<Integer,List<AssignmentRecord>> el : data.entrySet()) {
                TerritoryGroup group = new TerritoryGroup();
                group.number = el.getKey();
                group.records = el.getValue().stream().map(TerritoryGroupRecord::new).toList();
                list.add(group);
            }
            return list;
        }
    }

    @Data
    @NoArgsConstructor
    public static class TerritoryGroupRecord {
        private UUID recordId;
        private String assignedTo;
        private LocalDate assignedDate;
        private LocalDate completedDate;

        public TerritoryGroupRecord(AssignmentRecord data) {
            this.recordId = data.getId();
            this.assignedTo = data.getAssignedTo();
            this.assignedDate = data.getAssignedDate();
            this.completedDate = data.getCompletedDate();
        }
    }
}
