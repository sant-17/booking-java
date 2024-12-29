package org.utils;

import java.text.Normalizer;
import java.util.List;

public class StringUtility {

    private StringUtility() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean compareIgnoringAccentsAndCase(String firstStr, String secondStr){
        String firstNormalizedStr = Normalizer.normalize(firstStr, Normalizer.Form.NFD).replaceAll("\\p{M}", "").toLowerCase();
        String secondNormalizedStr = Normalizer.normalize(secondStr, Normalizer.Form.NFD).replaceAll("\\p{M}", "").toLowerCase();

        return firstNormalizedStr.equals(secondNormalizedStr);
    }

    public static String getStringElementByIndexModule(List<String> list, int index) {
        if (index < list.size()) {
            return list.get(index);
        }

        int indexModule = index % list.size();
        return list.get(indexModule);
    }
}
