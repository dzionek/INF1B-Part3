import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class HelperForMyself {
    public static void main(String[] args) {
        String[] myArray = {
                "ewabge",
                "ewan",
                "awd",
                "zan",
                "guan",
                "bartosz",
                "123sd"
        };
        HashMap<Character, ArrayList<String>> dict = new HashMap<>();
        for (String entry : myArray) {
            Character firstLetter = entry.charAt(0);
            if (dict.containsKey(firstLetter)) {
                dict.get(firstLetter).add(entry);
            } else {
                ArrayList<String> titles = new ArrayList<>();
                titles.add(entry);
                dict.put(firstLetter, titles);
            }
        }
        System.out.println(dict);


//        for (String title : myArray) {
//            String firstLetter = String.valueOf(title.charAt(0));
//
//        }
    }
}
