package io.github.cadnunsdimir.congespapi.domain.models;

import io.github.cadnunsdimir.congespapi.entities.meetings.Brother;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class BrotherAssigner {
    @NonNull
    private List<Brother> brothers;
    @NonNull
    private String type;
    private int index = 0;

    public Brother next(List<Object> ignoreList){
        if(this.index >= this.brothers.size()){
            this.index = 0;
        }
        var brother = brothers.get(index);
        index++;
        if(!ignoreList.contains(brother.getName()))
            return brother;
        return next(ignoreList);
    }
}
