package org.webcrawler.parser.util;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.webcrawler.parser.util.ParserUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class ParserUtilTest {

    public static Iterable<Object[]> positiveDifferentTypeOfSeeds() {
        return Arrays.asList(new Object[][] {
                {"<link href=\"https://www.gstatic.com/images/icons/material/anim/mspin/mspin_googblue_medium.css\" rel=\"stylesheet\" type=\"text/css\">",
                        Collections.singletonList("https://www.gstatic.com/images/icons/material/anim/mspin/mspin_googblue_medium.css")},
                {"<script href=\"http:///translate/releases/twsfe_w_20200622_RC00/r/js/translate_m_ru.js\"></script>",
                        Collections.singletonList("http:///translate/releases/twsfe_w_20200622_RC00/r/js/translate_m_ru.js")},
                {"<a class=\"gb_D gb_Ra gb_i\" aria-label=\"Аккаунт Google: Oleg\" href=\"https://translate.google.com/\" role=\"button\" tabindex=\"0\"><img class=\"gb_Ia gbii\" src=\"https://lh3.googleusercontent.com/ogw/ADGmqu_IPT7i33b-lQw8_Okf6o6EwChPQpvpWs1U3w6u=s32-c-mo\" srcset=\"https://lh3.googleusercontent.com/ogw/ADGmqu_IPT7i33b-lQw8_Okf6o6EwChPQpvpWs1U3w6u=s32-c-mo 1x, https://lh3.googleusercontent.com/ogw/ADGmqu_IPT7i33b-lQw8_Okf6o6EwChPQpvpWs1U3w6u=s64-c-mo 2x \" alt=\"\" aria-hidden=\"true\"></a><div class=\"gb_5a\"></div>",
                        Collections.singletonList("https://translate.google.com/")},
                {"<html <a href=\"https://hello.html\">oleg</a>>></html>",
                        Collections.singletonList("https://hello.html")},
                {"<html <a <link href=\"https://2hello.html\">>oleg</a>>></html><doctype><href=\"http://hi.com\"><aa>\"",
                        Arrays.asList("https://2hello.html", "http://hi.com")}
        });
    }

    public static Iterable<Object[]> negativeDifferentTypeOfSeeds() {
        return Arrays.asList(new Object[][] {
                {"<link href=\"//www.gstatic.com/images/icons/material/anim/mspin/mspin_googblue_medium.css\" rel=\"stylesheet\" type=\"text/css\">",
                        Collections.emptyList()},
                {"<script src=\"http:///translate/releases/twsfe_w_20200622_RC00/r/js/translate_m_ru.js\"></script>",
                        Collections.emptyList()},
                {"<a href=\"httpsss://translate.google.com/\" role=\"button\" tabindex=\"0\"><img class=\"gb_Ia gbii\" src=\"https://lh3.googleusercontent.com/ogw/ADGmqu_IPT7i33b-lQw8_Okf6o6EwChPQpvpWs1U3w6u=s32-c-mo\" srcset=\"https://lh3.googleusercontent.com/ogw/ADGmqu_IPT7i33b-lQw8_Okf6o6EwChPQpvpWs1U3w6u=s32-c-mo 1x, https://lh3.googleusercontent.com/ogw/ADGmqu_IPT7i33b-lQw8_Okf6o6EwChPQpvpWs1U3w6u=s64-c-mo 2x \" alt=\"\" aria-hidden=\"true\"></a><div class=\"gb_5a\"></div>",
                        Collections.emptyList()},
                {"", Collections.emptyList()},
                {"<html <a <link hreff=\"https://2hello.html\">>oleg</a>>></html><doctype><href=\"//hi.com><aa>\"",
                        Collections.emptyList()}
        });
    }

    @Test
    @Parameters(method = "positiveDifferentTypeOfSeeds")
    public void shouldReturnPositiveSeeds(String input, List<String> expectedSeeds) {
        List<String> seeds = ParserUtil.findSeedsFromBody(input);
        assertEquals(expectedSeeds, seeds);
    }

    @Test
    @Parameters(method = "positiveDifferentTypeOfSeeds")
    public void shouldReturnPositiveSimilarSizes(String input, List<String> expectedSeeds) {
        List<String> seeds = ParserUtil.findSeedsFromBody(input);
        assertEquals(expectedSeeds.size(), seeds.size());
    }

    @Test
    @Parameters(method = "negativeDifferentTypeOfSeeds")
    public void shouldReturnEmptyCollections(String input, List<String> expectedSeeds) {
        List<String> seeds = ParserUtil.findSeedsFromBody(input);
        assertEquals(expectedSeeds, seeds);
    }

    @Test
    @Parameters(method = "negativeDifferentTypeOfSeeds")
    public void shouldReturnNegativeSimilarSizes(String input, List<String> expectedSeeds) {
        List<String> seeds = ParserUtil.findSeedsFromBody(input);
        assertEquals(expectedSeeds.size(), seeds.size());
    }

    @Test
    public void shouldReturnEmptySeeds() {
        List<String> seeds = ParserUtil.findSeedsFromBody("");
        assertEquals(Collections.emptyList(), seeds);
    }

}