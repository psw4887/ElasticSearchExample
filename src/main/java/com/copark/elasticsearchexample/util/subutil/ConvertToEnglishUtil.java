package com.copark.elasticsearchexample.util.subutil;

import lombok.Getter;

/**
 * 알파벳으로 변환
 * 박세완 -> qkrtpdhks, 천재 -> cjswo
 *
 * @version 1.0.0
 */
@Getter
public class ConvertToEnglishUtil {

    /**
     * 초성 - 가(ㄱ), 날(ㄴ) 닭(ㄷ)
     */
    private final String[] arrChoSungEng;

    /**
     * 중성 - 가(ㅏ), 야(ㅑ), 뺨(ㅑ)
     */
    private final String[] arrJungSungEng;

    /**
     * 종성 - 가(없음), 갈(ㄹ) 천(ㄴ)
     */
    private final String[] arrJongSungEng;

    /**
     * 단일 자음 - ㄱ,ㄴ,ㄷ,ㄹ... (ㄸ,ㅃ,ㅉ은 단일자음(초성)으로 쓰이지만 단일자음으론 안쓰임)
     */
    private final String[] arrSingleJaumEng;

    public ConvertToEnglishUtil() {
        this.arrChoSungEng = new String[]{ "r", "R", "s", "e", "E",
                "f", "a", "q", "Q", "t", "T", "d", "w",
                "W", "c", "z", "x", "v", "g" };

        this.arrJungSungEng = new String[]{ "k", "o", "i", "O",
                "j", "p", "u", "P", "h", "hk", "ho", "hl",
                "y", "n", "nj", "np", "nl", "b", "m", "ml",
                "l" };

        this.arrJongSungEng = new String[]{ "", "r", "R", "rt",
                "s", "sw", "sg", "e", "f", "fr", "fa", "fq",
                "ft", "fx", "fv", "fg", "a", "q", "qt", "t",
                "T", "d", "w", "c", "z", "x", "v", "g" };

        this.arrSingleJaumEng = new String[]{"r", "R", "rt",
                "s", "sw", "sg", "e", "E", "f", "fr", "fa", "fq",
                "ft", "fx", "fv", "fg", "a", "q", "Q", "qt", "t",
                "T", "d", "w", "W", "c", "z", "x", "v", "g"};
    }

}
