package io.github.cadnunsdimir.congespapi.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.security.Key;

@AllArgsConstructor
@Getter
public enum AssignmentTypeEnum {
    WATCHTOWER_STUDY_CONDUCTOR("WatchtowerStudyConductor");
    private final String type;

    @Override
    public String toString(){
        return type;
    }
}
