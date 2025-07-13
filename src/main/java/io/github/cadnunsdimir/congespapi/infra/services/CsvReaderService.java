package io.github.cadnunsdimir.congespapi.infra.services;

import io.github.cadnunsdimir.congespapi.infra.data.entities.meetings.AssignmentType;
import io.github.cadnunsdimir.congespapi.infra.data.entities.meetings.Brother;
import io.github.cadnunsdimir.congespapi.infra.data.repositories.meetings.AssignmentTypeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.*;

@ApplicationScoped
@AllArgsConstructor
public class CsvReaderService {
    private AssignmentTypeRepository assignmentTypeRepository;

    public List<Brother> toBrotherList(String csv) {
        var lines = csv.split("\n");
        var header = lines[0].split(",");
        Map<Integer, AssignmentType> headerMap = new HashMap<>();

        for (int i = 1; i < header.length; i++) {
            var typeAsString = header[i];
            var type = this.assignmentTypeRepository.findByType(typeAsString);

            if (type == null) {
                type = new AssignmentType();
                type.setId(UUID.randomUUID());
                type.setType(typeAsString);    
            }
            
            headerMap.put(i, type);
        }

        var brothers = new ArrayList<Brother>();

        for (int line = 1; line < lines.length; line++) {
            var brotherLine = lines[line].split(",");
            var brother = new Brother();
            brother.setName(brotherLine[0]);
            var assignment = new ArrayList<AssignmentType>();

            for (int column = 1; column < brotherLine.length; column++) {
                var type = headerMap.get(column);
                boolean allowedAssigment = brotherLine[column].equals("x");
                
                if(allowedAssigment && type != null)
                    assignment.add(headerMap.get(column));
            }
            brother.setAssignments(assignment);
            brothers.add(brother);
        }
        
        return brothers;
    }
}
