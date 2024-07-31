package utils;

import java.util.List;
import namedEntities.NamedEntity;

public class StadisticsPrinter {
    private List<TuplaEntity> categories;
    private List<TuplaEntity> topics;

    public StadisticsPrinter(List<TuplaEntity> categories, List<TuplaEntity> topics) {
        this.categories = categories;
        this.topics = topics;
    }

    public void printStatsCat() {

        for (TuplaEntity t : categories) {
            System.out.println("*****************" + t.getKey() + ":******************* ");
            for (NamedEntity p : t.getValue()) {
                System.out.println(p.getCanonicalName() + "(" + p.getCounter() + ")");
            }

        }

    }

    public void printStatsTopic() {
        for (TuplaEntity t : topics) {
            System.out.println("*****************" + t.getKey() + ":******************* ");
            for (NamedEntity p : t.getValue()) {
                System.out.println(p.getCanonicalName() + "(" + p.getCounter() + ")");
            }

        }

    }

    public List<TuplaEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<TuplaEntity> categories) {
        this.categories = categories;
    }

    public List<TuplaEntity> getTopics() {
        return topics;
    }

    public void setTopics(List<TuplaEntity> topics) {
        this.topics = topics;
    }

    public void addCategory(TuplaEntity category) {
        this.categories.add(category);
    }

    public void addTopic(TuplaEntity topic) {
        this.topics.add(topic);
    }

}
