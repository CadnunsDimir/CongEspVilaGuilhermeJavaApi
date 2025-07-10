package io.github.cadnunsdimir.congespapi.domain.models;

import io.github.cadnunsdimir.congespapi.entities.meetings.Brother;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Data
@RequiredArgsConstructor
public class BrotherAssigner {
    @NonNull
    private List<Brother> brothers;
    @NonNull
    private String type;
    private int index = 0;
    private int safetyRecursiveCounter = 0;

    public Brother next(List<Object> ignoreList, UUID columnId){
        if(this.index >= this.brothers.size()){
            this.index = 0;
            reorderBrothers();
        }
        var brother = brothers.get(index);
        var brotherName = brother.getName();
        var notInIgnoreList = !ignoreList.contains(brotherName);

        index++;
        if(notInIgnoreList) {
            if(type.equals("AudioVideo")) {
                System.out.println(index+"-"+brotherName);
            }
            safetyRecursiveCounter = 0;
            return brother;
        }

        safetyRecursiveCounter++;
        return next(ignoreList, columnId);
    }

    private void reorderBrothers() {
        var newList = new ArrayList<Brother>();
        var firstItem = this.brothers.getFirst();
        for (int i = 1; i < this.brothers.size(); i++) {
            newList.add(this.brothers.get(i));
        }
        newList.add(firstItem);
        this.brothers = newList;
    }
}
