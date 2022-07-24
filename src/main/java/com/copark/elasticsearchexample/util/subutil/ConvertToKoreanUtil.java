package com.copark.elasticsearchexample.util.subutil;

import com.copark.elasticsearchexample.util.Converter;

public class ConvertToKoreanUtil {
    enum CodeType {CHOSUNG, JUNGSUNG, JONGSUNG}

    /**
     * 해당 문자에 따른 코드를 추출한다.
     *
     * @param type 초성 : chosung, 중성 : jungsung, 종성 : jongsung 구분
     * @param word 해당 문자
     */
    public int getCode(Converter.CodeType type, String word) {
        // 초성
        String init = "rRseEfaqQtTdwWczxvg";
        // 중성
        String[] mid =
                { "k", "o", "i", "O", "j", "p", "u", "P", "h", "hk", "ho", "hl", "y", "n", "nj",
                        "np", "nl", "b", "m", "ml", "l" };
        // 종성
        String[] fin =
                { "r", "R", "rt", "s", "sw", "sg", "e", "f", "fr", "fa", "fq", "ft", "fx", "fv",
                        "fg", "a", "q", "qt", "t", "T", "d", "w", "c", "z", "x", "v", "g" };

        switch (type) {
            case CHOSUNG:
                int index = init.indexOf(word);
                if (index != -1) {
                    return index * 21 * 28;
                }
                break;
            case JUNGSUNG:
                for (int i = 0; i < mid.length; i++) {
                    if (mid[i].equals(word)) {
                        return i * 28;
                    }
                }
                break;
            case JONGSUNG:
                for (int i = 0; i < fin.length; i++) {
                    if (fin[i].equals(word)) {
                        return i + 1;
                    }
                }
                break;
            default:
                log.error("잘못된 타입 입니다");
        }
        return -1;
    }
    // 한 자로 된 중성값을 리턴한다
    // 인덱스를 벗어낫다면 -1을 리턴
    public int getSingleMedial(int i, String eng) {
        if ((i + 1) <= eng.length()) {
            return getCode(Converter.CodeType.JUNGSUNG, eng.substring(i, i + 1));
        }
        return -1;
    }

    // 두 자로 된 중성을 체크하고, 있다면 값을 리턴한다.
    // 없으면 리턴값은 -1
    public int getDoubleMedial(int i, String eng) {
        if ((i + 2) > eng.length()) {
            return -1;
        }
        return getCode(Converter.CodeType.JUNGSUNG, eng.substring(i, i + 2));
    }

    // 한 자로된 종성값을 리턴한다
    // 인덱스를 벗어낫다면 -1을 리턴
    public int getSingleFinal(int i, String eng) {
        if ((i + 1) <= eng.length()) {
            return getCode(Converter.CodeType.JONGSUNG, eng.substring(i, i + 1));
        }
        return -1;
    }

    // 두 자로된 종성을 체크하고, 있다면 값을 리턴한다.
    // 없으면 리턴값은 -1
    public int getDoubleFinal(int i, String eng) {
        if ((i + 2) > eng.length()) {
            return -1;
        }
        return getCode(Converter.CodeType.JONGSUNG, eng.substring(i, i + 2));
    }

}
