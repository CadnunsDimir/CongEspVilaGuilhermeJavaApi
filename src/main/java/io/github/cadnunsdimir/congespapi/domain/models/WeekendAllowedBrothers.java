package io.github.cadnunsdimir.congespapi.domain.models;

import java.util.List;

import io.github.cadnunsdimir.congespapi.infra.data.entities.meetings.Brother;
import lombok.Getter;

@Getter
public class WeekendAllowedBrothers {
    private List<String> asPresident;
    private List<String> asReader;

    public WeekendAllowedBrothers(List<Brother> presidents, List<Brother> readers) {
        asPresident = mapName(presidents);
        asReader = mapName(readers);
    }

    private List<String> mapName(List<Brother> brothers) {
       return brothers.stream().map(b-> b.getName()).toList();
    }
}
