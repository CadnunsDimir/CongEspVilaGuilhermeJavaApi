package oi.github.cadnunsdimir.congespapi.infra.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import oi.github.cadnunsdimir.congespapi.entities.meetings.AssignmenType;
import oi.github.cadnunsdimir.congespapi.entities.meetings.Brother;
import oi.github.cadnunsdimir.congespapi.infra.repositories.meetings.AssignmenTypeRepository;

@ApplicationScoped
@AllArgsConstructor
public class CsvReaderService {
    private AssignmenTypeRepository assignmenTypeRepository;

    public List<Brother> toBrotherList(String csv) {
        var lines = csv.split("\n");
        var header = lines[0].split(",");
        Map<Integer, AssignmenType> headerMap = new HashMap<>();

        for (int i = 1; i < header.length; i++) {
            var typeAsString = header[i];
            var type = this.assignmenTypeRepository.findByType(typeAsString);

            if (type == null) {
                type = new AssignmenType();
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
            var assignment = new ArrayList<AssignmenType>();

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
