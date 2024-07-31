package namedEntities.categories;

import namedEntities.NamedEntity;

public class Organization extends NamedEntity {
    private String canonicalForm;
    private int numberOfEmployees;
    private String type;

    public Organization(String canonicalName, String canonicalForm, int numberOfEmployees, String type) {
        super(canonicalName, "Organization");
        this.canonicalForm = canonicalForm;
        this.numberOfEmployees = numberOfEmployees;
        this.type = type;
    }

    public String getCanonicalForm() {
        return canonicalForm;
    }

    public void setCanonicalForm(String canonicalForm) {
        this.canonicalForm = canonicalForm;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
