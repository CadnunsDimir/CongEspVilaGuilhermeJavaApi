package oi.github.cadnunsdimir.congespapi.domain.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import oi.github.cadnunsdimir.congespapi.entities.meetings.Brother;

/*
 * 
 * 
 * class BrotherAsigner:
    def __init__(self, type, table_column_names, all_brothers_list):
        self.type = type
        self.filtered_brothers = list(map(lambda x: x[0], filter((lambda row: row[table_column_names.index('Acomodacion')] == 'x'), all_brothers_list)))
        self.index = 0
    def next(self, ignore_list = []):
        if(self.index >= len(self.filtered_brothers)):
            self.index = 0
        brother = self.filtered_brothers[self.index]
        self.index += 1
        return brother if brother not in date else self.next(ignore_list)
 */
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
