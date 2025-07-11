package io.github.cadnunsdimir.congespapi.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.security.Key;

@AllArgsConstructor
@Getter
public enum AssignmentTypeEnum {
    WATCHTOWER_STUDY_CONDUCTOR("ConducAtal"),
    WATCHTOWER_STUDY_READER("LectorAtal"),
    WEEKEND_MEETING_PRESIDENT("PresFinSem");
    private final String type;

    @Override
    public String toString(){
        return type;
    }
}
