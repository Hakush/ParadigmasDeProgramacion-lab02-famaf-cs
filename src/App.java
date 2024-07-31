import feed.Article;
import feed.FeedParser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import namedEntities.NamedEntity;
import namedEntities.heuristics.*;
import utils.Config;
import utils.DatabaseData;
import utils.EntityParser;
import utils.FeedsData;
import utils.JSONParser;
import utils.StadisticsPrinter;
import utils.TopicsStadistics;
import utils.TuplaEntity;
import utils.UserInterface;

public class App {

    public static final Pattern SPACE = Pattern.compile(" ");

    public static void main(String[] args) {

        List<FeedsData> feedsDataArray = new ArrayList<>();
        try {
            feedsDataArray = JSONParser.parseJsonFeedsData("src/data/feeds.json");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        UserInterface ui = new UserInterface();
        Config config = ui.handleInput(args);
        // Config config = ui.handleInput(new String[]{"-ne", "Capitalized"}); // (para
        // hardcodear testeos)

        run(config, feedsDataArray);

    }

    private static void run(Config config, List<FeedsData> feedsDataArray) {

        if (feedsDataArray == null || feedsDataArray.size() == 0) {
            System.out.println("No feeds data found");
            return;
        }

        // Filter feedsDataArray with the selected feed key
        if (config.getFeedKey() != null) {
            List<FeedsData> feedsDataArrayAux = new ArrayList<>();
            for (FeedsData feedData : feedsDataArray) {
                if (feedData.getLabel().equals(config.getFeedKey())) {
                    feedsDataArrayAux.add(feedData);
                }
            }
            feedsDataArray = feedsDataArrayAux;
        }

        // Populate feedsDataArray with articles from corresponding feeds
        for (FeedsData feedData : feedsDataArray) {
            try {
                String feedXML = FeedParser.fetchFeed(feedData.getUrl());
                List<Article> articles = FeedParser.parseXML(feedXML);
                feedData.addAll(articles);
            } catch (Exception e) {
                System.out.println("Error fetching feed: " + feedData.getLabel());
                e.printStackTrace();
            }
        }

        if (config.getPrintFeed()) {
            System.out.println("Printing feed(s) ");
            // Prints the fetched feed
            for (FeedsData feedData : feedsDataArray) {
                System.out.println("\n\nFeed: " + feedData.getLabel());
                System.out.println("URL: " + feedData.getUrl());
                System.out.println("Articles: ");
                for (Article article : feedData.getArticles()) {
                    article.prettyPrint();
                }
            }
        }

        if (config.getComputeNamedEntities()) {

            try {
                String heuristicName = config.getHeuristic(); // Esto vendría del input del usuario
                // Mapear config.getHeuristic() con su nombre de clase
                String heuristicClassName = HeuristicFactory.getHeuristicClassName(heuristicName);

                if (heuristicClassName == null) {
                    System.out.println("Heuristic not found: " + heuristicName);
                    System.exit(1);
                    return;
                }

                System.out.println("Computing named entities using " + config.getHeuristic() + " heuristic");
                List<List<String>> namedEntities = new ArrayList<>();
                // Cargar la clase
                Class<?> heuristicClass = Class.forName(heuristicClassName);
                // Crear una instancia de la clase
                Heuristic heuristic = (Heuristic) heuristicClass.getDeclaredConstructor().newInstance();
                // compute named entities using the selected heuristic
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("articles.txt"))) {
                        for (FeedsData feedData : feedsDataArray) {
                            for (Article article : feedData.getArticles()) {
                                try {
                                    String title = article.getTitle() != null ? article.getTitle() : "" ;
                                    String description = article.getDescription() != null ? article.getDescription() : "";
                                    String text = title + " \n" + description + " \n\n";
                                    
                                    // Print into file
                                    writer.write(text);

                                    List<String> wordsList = Arrays.asList(SPACE.split(text));

                                    for (String word : wordsList) {
                                        // Apply heuristic to the word
                                        namedEntities.add(heuristic.extractCandidates(word));
                                    }

                                } catch (Exception e) {
                                    System.out.println("Error writing article: " + article.getTitle());
                                    e.printStackTrace();
                                }
                            }   
                        }
                    } catch (IOException e) {
                        System.out.println("Error writing to file");
                        e.printStackTrace();
                    }
                List<DatabaseData> databaseList = new ArrayList<>();
                try {
                    databaseList = JSONParser.parseJsonDatabaseData("src/data/dictionary.json");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }

                // Transform namedEntities to List<String>
                List<String> namedEntitiesList = new ArrayList<>();
                for (List<String> entities : namedEntities) {
                    namedEntitiesList.addAll(entities);
                }

                EntityParser ep = new EntityParser(namedEntitiesList);

                List<NamedEntity> persons = ep.parsePerson(databaseList);
                List<NamedEntity> organizations = ep.parseOrganization(databaseList);
                List<NamedEntity> locations = ep.parseLocation(databaseList);
                List<NamedEntity> others = ep.parseOther(databaseList);

                List<TuplaEntity> categories = new ArrayList<>();
                categories.add(new TuplaEntity("PERSON", persons));
                categories.add(new TuplaEntity("ORGANIZATION", organizations));
                categories.add(new TuplaEntity("LOCATION", locations));
                categories.add(new TuplaEntity("OTHER", others));

                TopicsStadistics ts = new TopicsStadistics();

                ts.addPerson(persons);
                ts.addOrganization(organizations);
                ts.addLocation(locations);
                ts.addOther(others);

                List<TuplaEntity> topics = new ArrayList<>();
                topics.add(new TuplaEntity("SPORTS", ts.getSports()));
                topics.add(new TuplaEntity("POLITICS", ts.getPolitics()));
                topics.add(new TuplaEntity("ECONOMY", ts.getEconomy()));
                topics.add(new TuplaEntity("HEALTH", ts.getHealth()));
                topics.add(new TuplaEntity("TECHNOLOGY", ts.getTechnology()));
                topics.add(new TuplaEntity("ENTERTAINMENT", ts.getEntertainment()));
                topics.add(new TuplaEntity("OTHER", ts.getOther()));

                StadisticsPrinter sp = new StadisticsPrinter(categories, topics);

                System.out.println("\nStats ");

                if (config.getStatsFormat().equals("cat")) {
                    System.out.println("By Category:\n");
                    // Estadisticas por categoria
                    sp.printStatsCat();
                } else if (config.getStatsFormat().equals("topic")) {
                    System.out.println("By Topic:\n");
                    // Estadisticas por topico
                    sp.printStatsTopic();
                }
            } catch (Exception e) {
                System.out.println("Error running application");
                e.printStackTrace();
            }
        }
        System.out.println("");
    }
}
