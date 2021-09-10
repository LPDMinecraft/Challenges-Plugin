package lcraft.challenges.api.languages.impl;

import lcraft.challenges.api.languages.Language;

public class German extends Language {

    @Override
    public String getName() {
        return "Deutsch";
    }

    @Override
    public String getEnglishName() {
        return "German";
    }

    @Override
    public String getShort() {
        return "da";
    }

}
