package io.github.cadnunsdimir.congespapi.infra.repositories.meetings;

import io.github.cadnunsdimir.congespapi.entities.meetings.PublicTalk;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class PublicTalkRepository implements PanacheRepository<PublicTalk> {

    @Transactional
    public void updateOrCreateNew(PublicTalk publicTalk){
        var entity = find("date", publicTalk.getDate())
                .firstResultOptional()
                .map(db-> {
                    db.setCongregation(publicTalk.getCongregation());
                    db.setIsLocal(publicTalk.getIsLocal());
                    db.setOutlineNumber(publicTalk.getOutlineNumber());
                    db.setSpeaker(publicTalk.getSpeaker());
                    db.setPublicTalkTheme(publicTalk.getPublicTalkTheme());
                    return db;
                })
                .orElse(publicTalk);

        persist(entity);
    }

    public List<PublicTalk> listByMonth(int month, int year) {
        var firstDay = LocalDate.of(year, month, 1);
        var lastDay = firstDay.plusMonths(1);

        return listBetweenDates(firstDay, lastDay);
    }

    public List<PublicTalk> listBetweenDates(LocalDate firstDate, LocalDate lastDate) {
        return list("from PublicTalk where date between ?1 and ?2", firstDate, lastDate);
    }
}
