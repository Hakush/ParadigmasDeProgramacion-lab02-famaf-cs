package namedEntities.heuristics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonsHeuristics implements Heuristic {
    String filepath = "src/data/personas.txt";

    @Override
    public List<String> extractCandidates(String text) {

        List<String> candidates = new ArrayList<>();

        try (FileReader fileReader = new FileReader(filepath);
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            text = text.replaceAll("[-+.^:,\"]", "");
            text = Normalizer.normalize(text, Normalizer.Form.NFD);
            text = text.replaceAll("\\p{M}", "");

            List<String> txt = new ArrayList<>();
            String line = bufferedReader.readLine();
            while (line != null) {
                txt.add(line);
                line = bufferedReader.readLine();
            }

            String regex = "\\b(" + String.join("|", txt) + ")\\b";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                candidates.add(matcher.group());
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error al leer en el archivo.");
            System.out.println("Exception: " + e.getMessage());
        }

        return candidates;
    }
}
