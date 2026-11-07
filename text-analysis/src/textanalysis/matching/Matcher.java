package textanalysis.matching;

import java.util.List;

public interface Matcher {

    List<Integer> search(String text, String pattern);


    long comparisons();

    String name();
}
