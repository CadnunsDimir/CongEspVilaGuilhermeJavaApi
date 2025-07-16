package io.github.cadnunsdimir.congespapi.domain.services;

import io.github.cadnunsdimir.congespapi.domain.models.TerritoryAssignmentPatchRecord;
import io.github.cadnunsdimir.congespapi.domain.models.TerritoryAssignmentRecord;
import io.github.cadnunsdimir.congespapi.domain.models.TerritoryAssignmentSheetData;
import io.github.cadnunsdimir.congespapi.infra.data.entities.territory.assignment.AssignmentRecord;
import io.github.cadnunsdimir.congespapi.infra.data.entities.territory.assignment.Sheet;
import io.github.cadnunsdimir.congespapi.infra.data.entities.territory.assignment.TerritoryNumber;
import io.github.cadnunsdimir.congespapi.infra.data.repositories.territory.assignment.RecordRepository;
import io.github.cadnunsdimir.congespapi.infra.data.repositories.territory.assignment.SheetRepository;
import io.github.cadnunsdimir.congespapi.infra.data.repositories.territory.assignment.TerritoryNumberRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.Month;

@ApplicationScoped
public class TerritoryAssignmentService {
    private final SheetRepository sheetRepository;
    private final TerritoryNumberRepository territoryNumberRepository;
    private final RecordRepository recordRepository;

    public TerritoryAssignmentService(
        SheetRepository sheetRepository, 
        TerritoryNumberRepository territoryNumberRepository,
        RecordRepository recordRepository) {
        this.sheetRepository = sheetRepository;
        this.territoryNumberRepository = territoryNumberRepository;
        this.recordRepository = recordRepository;
    }

    @Transactional
    public void addRecord(final TerritoryAssignmentRecord recordViewModel){
        Sheet sheet = defineSheet(recordViewModel.getAssignedDate());
        TerritoryNumber territoryNumber = defineTerritory(recordViewModel);
        AssignmentRecord dbRecord = buildRecord(recordViewModel, sheet, territoryNumber);
        recordRepository.persist(dbRecord);
    }

    private TerritoryNumber defineTerritory(TerritoryAssignmentRecord recordViewModel) {
        var territory = territoryNumberRepository.findByNumber(recordViewModel.getTerritoryNumber());

        if (territory == null) {
            territory = new TerritoryNumber();
            territory.setNumber(recordViewModel.getTerritoryNumber());
            territoryNumberRepository.persist(territory);
        }

        return territory;
    }

    private Sheet defineSheet(LocalDate date) {
        var serviceYear = date.getMonth().getValue() >= Month.SEPTEMBER.getValue() ? date.getYear() + 1 : date.getYear();;
        Sheet sheet = sheetRepository.findLastServiceYear(serviceYear);
        boolean isFirstSheetOfTheServiceYear = sheet == null;

        if (isFirstSheetOfTheServiceYear) {
            sheet = new Sheet();
            sheet.setServiceYear(serviceYear);
            sheetRepository.persist(sheet);
        }

        return sheet;
    }

    private AssignmentRecord buildRecord(final TerritoryAssignmentRecord assignmentRecord, Sheet sheet, TerritoryNumber territoryNumber) {
        return AssignmentRecord.builder()
            .assignedTo(assignmentRecord.getAssignedTo())
            .assignedDate(assignmentRecord.getAssignedDate())
            .completedDate(assignmentRecord.getCompletedDate())
            .sheet(sheet)
            .territoryNumber(territoryNumber)
            .build();
    }

    @Transactional
    public TerritoryAssignmentSheetData getCurrentSheetData() {
        var sheet = defineSheet(LocalDate.now());
        var records = recordRepository.listBySheetId(sheet.getId());
        var lastCompletedDate = recordRepository.listLastCompletedDateByCardFromLastYearService(sheet.getServiceYear() - 1);

        return new TerritoryAssignmentSheetData(sheet, records, lastCompletedDate, 4);
    }

    @Transactional
    public void patchRecord(TerritoryAssignmentPatchRecord patchedRecord) {
        recordRepository.updateCompletedDate(patchedRecord.getRecordId(), patchedRecord.getCompletedDate());
    }
}
