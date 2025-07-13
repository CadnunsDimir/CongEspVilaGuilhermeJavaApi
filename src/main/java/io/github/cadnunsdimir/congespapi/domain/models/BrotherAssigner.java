package io.github.cadnunsdimir.congespapi.domain.models;

import io.github.cadnunsdimir.congespapi.entities.meetings.Brother;
import lombok.Getter;
import lombok.NonNull;

import java.util.*;

@Getter
public class BrotherAssigner {
    @NonNull
    private List<Brother> brothers;
    @NonNull
    private String type;
    private Queue<Brother> fifo;
    private boolean keepOrder;

    public BrotherAssigner(List<Brother> brothers, String type, boolean keepOrder) {
        this.brothers = brothers;
        this.type = type;
        this.keepOrder = keepOrder;
        fifo = new LinkedList<>(brothers);
    }

    public Brother next(List<Object> ignoreList){
        if(fifo.isEmpty()){
            if (!keepOrder) {
                reorderBrothers();
            }
            fifo.addAll(brothers);
        }
        var brother = fifo.remove();
        var brotherName = brother.getName();
        var notInIgnoreList = !ignoreList.contains(brotherName);

        if(notInIgnoreList) {
            return brother;
        }

        fifo.add(brother);
        if (fifo.size() == 1) {
            fifo.remove();
        }
        return next(ignoreList);
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

    public void alternateOrder() {
        var queue = new LinkedList<>(brothers);
        var newList = new ArrayList<Brother>();

        while (!queue.isEmpty()) {
            newList.add(queue.remove());
            if (!queue.isEmpty()) {
                newList.add(queue.remove(queue.size() - 1));
            }
        }

        this.brothers = newList;
        fifo = new LinkedList<>(newList);
    }

    public void print() {
        System.out.println("------------------------------");
        System.out.println("Type: "+type);
        System.out.println("\tItens: ");
        brothers.forEach(b-> System.out.println("\t\t"+brothers.indexOf(b)+" : "+b.getName()));
    }
}
