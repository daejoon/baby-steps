package com.ddoong2;

import java.util.HashMap;
import java.util.Map;

public class Parser {

    private static final int CONSONANT_START_CODE = 0x1100;
    private static final int SYLLABLE_START_CODE = 0xAC00;
    private static final int SYLLABLE_END_CODE = 0xD7A3;
    private static final int CYCLE = 588;

    private final Map<Character, Character> compatibleInitial = new HashMap<>();

    public Parser() {
        char code = 0x1100;
        compatibleInitial.put('ㄱ', code++);
        compatibleInitial.put('ㄲ', code++);
        compatibleInitial.put('ㄴ', code++);
        compatibleInitial.put('ㄷ', code++);
        compatibleInitial.put('ㄸ', code++);
        compatibleInitial.put('ㄹ', code++);
        compatibleInitial.put('ㅁ', code++);
        compatibleInitial.put('ㅂ', code++);
        compatibleInitial.put('ㅃ', code++);
        compatibleInitial.put('ㅅ', code++);
        compatibleInitial.put('ㅆ', code++);
        compatibleInitial.put('ㅇ', code++);
        compatibleInitial.put('ㅈ', code++);
        compatibleInitial.put('ㅉ', code++);
        compatibleInitial.put('ㅊ', code++);
        compatibleInitial.put('ㅋ', code++);
        compatibleInitial.put('ㅌ', code++);
        compatibleInitial.put('ㅍ', code++);
        compatibleInitial.put('ㅎ', code++);
    }

    public boolean find(String search, String source) {
        checkValidation(search, source);

        if (isEmptyString(search)) {
            return true;
        }

        if (isLessThenSearchLength(search, source)) {
            return false;
        }

        int startIndex = getStartIndex(source, search.charAt(0));
        if ( startIndex == -1) {
            return false;
        }

        for (int index = startIndex, searchIndex=0; index < startIndex + search.length() ; index++, searchIndex++) {
            if (getCharToInitialSound(search.charAt(searchIndex)) != getCharToInitialSound(source.charAt(index))) {
                return false;
            }
        }

        return true;
    }

    private boolean isLessThenSearchLength(String search, String source) {
        return search.length() > source.length();
    }

    private void checkValidation(String search, String source) {
        if (search == null || source == null) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isEmptyString(String search) {
        return search.equalsIgnoreCase("");
    }

    private int getStartIndex(String source, char findSyllable) {

        for (int index = 0; index < source.length(); index++) {
            if ( getCharToInitialSound(findSyllable) == getCharToInitialSound(source.charAt(index))) {
                return index;
            }
        }
        return -1;
    }

    private char getCharToInitialSound(char syllable) {
        if (isHangul(syllable)) {
            return ((char) (CONSONANT_START_CODE + ((syllable - SYLLABLE_START_CODE) / CYCLE)));
        } else if ( isCompatibleHangul(syllable) ) {
            return compatibleInitial.get(syllable).charValue();
        }

        return syllable;
    }

    private boolean isCompatibleHangul(char syllable) {
        return compatibleInitial.containsKey(syllable);
    }

    private boolean isHangul(char syllable) {
        return  SYLLABLE_START_CODE <= syllable && syllable <= SYLLABLE_END_CODE;
    }
}
