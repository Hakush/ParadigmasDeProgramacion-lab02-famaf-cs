package namedEntities;

import java.util.ArrayList;
import java.util.List;

public abstract class NamedEntity {
    private String canonicalName;
    private List<String> topics = new ArrayList<>();
    private int counter;
    private String category;

    public NamedEntity(String canonicalName, String category) {
        this.canonicalName = canonicalName;
        this.topics.add("");
        this.counter = 1;
        this.category = category;
    }

    public String getCanonicalName() {
        return canonicalName;
    }

    public void setCanonicalName(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(String topic) {
        this.topics.add(topic);
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
