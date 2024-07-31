package utils;

import java.util.ArrayList;
import java.util.List;
import namedEntities.NamedEntity;
import namedEntities.categories.*;

public class EntityParser {
    private List<String> entities;

    public EntityParser(List<String> entities) {
        this.entities = entities;
    }

    public List<String> getEntities() {
        return entities;
    }

    public void setEntities(List<String> entities) {
        this.entities = entities;
    }

    public List<NamedEntity> parsePerson(List<DatabaseData> dictionary) {
        List<NamedEntity> persons = new ArrayList<>();

        for (Object e : this.entities) {
            for (DatabaseData data : dictionary) {
                for (String keyword : data.getKeywords()) {
                    if (keyword.equals(e)) {
                        if (data.getCategory().equals("PERSON")) {
                            boolean existsPerson = false;
                            for (NamedEntity p : persons) {
                                if (p.getCanonicalName().equals(data.getLabel())) {
                                    p.setCounter(p.getCounter() + 1);
                                    existsPerson = true;
                                    break;
                                }
                            }
                            if (!existsPerson) {
                                Person p = new Person(data.getLabel(), 0, "", "");
                                for (String s : data.getTopics()) {
                                    p.setTopics(s);
                                }
                                persons.add(p);
                            }
                        }
                    }
                }

            }
        }

        return persons;
    }

    public List<NamedEntity> parseOrganization(List<DatabaseData> dictionary) {
        List<NamedEntity> organizations = new ArrayList<>();

        for (Object e : this.entities) {
            for (DatabaseData data : dictionary) {
                for (String keyword : data.getKeywords()) {
                    if (keyword.equals(e)) {
                        if (data.getCategory().equals("ORGANIZATION")) {
                            boolean existsOrganization = false;
                            for (NamedEntity o : organizations) {
                                if (o.getCanonicalName().equals(data.getLabel())) {
                                    o.setCounter(o.getCounter() + 1);
                                    existsOrganization = true;
                                    break;
                                }
                            }
                            if (!existsOrganization) {
                                Organization o = new Organization(data.getLabel(), "", 0, "");
                                for (String s : data.getTopics()) {
                                    o.setTopics(s);
                                }
                                organizations.add(o);
                            }
                        }
                    }
                }
            }
        }

        return organizations;
    }

    public List<NamedEntity> parseLocation(List<DatabaseData> dictionary) {
        List<NamedEntity> locations = new ArrayList<>();

        for (Object e : this.entities) {
            for (DatabaseData data : dictionary) {
                for (String keyword : data.getKeywords()) {
                    if (keyword.equals(e)) {
                        if (data.getCategory().equals("LOCATION")) {
                            boolean existsLocation = false;
                            for (NamedEntity l : locations) {
                                if (l.getCanonicalName().equals(data.getLabel())) {
                                    l.setCounter(l.getCounter() + 1);
                                    existsLocation = true;
                                    break;
                                }
                            }
                            if (!existsLocation) {
                                Location l = new Location(data.getLabel(), "", "", "", "");
                                for (String s : data.getTopics()) {
                                    l.setTopics(s);
                                }
                                locations.add(l);
                            }
                        }
                    }
                }
            }
        }

        return locations;
    }

    public List<NamedEntity> parseOther(List<DatabaseData> dictionary) {
        List<NamedEntity> other = new ArrayList<>();

        for (Object e : this.entities) {
            for (DatabaseData data : dictionary) {
                for (String keyword : data.getKeywords()) {
                    if (keyword.equals(e)) {
                        if (data.getCategory().equals("OTHER")) {
                            boolean existsOther = false;
                            for (NamedEntity m : other) {
                                if (m.getCanonicalName().equals(data.getLabel())) {
                                    m.setCounter(m.getCounter() + 1);
                                    existsOther = true;
                                    break;
                                }
                            }
                            if (!existsOther) {
                                Other m = new Other(data.getLabel(), "");
                                for (String s : data.getTopics()) {
                                    m.setTopics(s);
                                }
                                other.add(m);
                            }
                        }
                    }
                }
            }
        }

        return other;
    }
}
