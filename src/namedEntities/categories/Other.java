package namedEntities.categories;

import namedEntities.NamedEntity;

public class Other extends NamedEntity {
    private String type;

    public Other(String canonicalName, String type) {
        super(canonicalName, "Other");
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
