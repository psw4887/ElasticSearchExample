package com.copark.elasticsearchexample.util;

import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Converter {
    enum CodeType {CHOSUNG, JUNGSUNG, JONGSUNG }
    static String ignoreChars = "`1234567890-=[]\\;',./~!@#$%^&*()_+{}|:\"<>? ";

    public String converter(final String word) {
        if (Pattern.matches("^[a-zA-Z]*$", word)) {
            return convertToKorean(word);
        }
        return convertToEnglish(word);
    }

    private String convertToEnglish(final String word) {
        StringBuilder resultEng = new StringBuilder();

        for (int i = 0; i < word.length(); i++) {

            /*  한글자씩 읽어들인다. */
            char chars = (char) (word.charAt(i) - 0xAC00);

            if (chars <= 11172) {
                /* A. 자음과 모음이 합쳐진 글자인경우 */

                /* A-1. 초/중/종성 분리 */
                int chosung = chars / (21 * 28);
                int jungsung = chars % (21 * 28) / 28;
                int jongsung = chars % (21 * 28) % 28;

                /* 알파벳으로 */
                resultEng.append(UnicodeKorean.arrChoSungEng[chosung]).append(UnicodeKorean.arrJungSungEng[jungsung]);
                if (jongsung != 0x0000) {
                    /* A-3. 종성이 존재할경우 result 에 담는다 */
                    resultEng.append(UnicodeKorean.arrJongSungEng[jongsung]);
                }

            } else {
                /* B. 한글이 아니거나 자음만 있을경우 */

                /* 알파벳으로 */
                if (chars >= 34097 && chars <= 34126) {
                    /* 단일자음인 경우 */
                    int jaum = (chars - 34097);
                    resultEng.append(UnicodeKorean.arrSingleJaumEng[jaum]);
                } else if (chars >= 34127 && chars <= 34147) {
                    /* 단일모음인 경우 */
                    int moum = (chars - 34127);
                    resultEng.append(UnicodeKorean.arrJungSungEng[moum]);
                } else {
                    /* 알파벳인 경우 */
                    resultEng.append(((char) (chars + 0xAC00)));
                }
            }
        }
        return resultEng.toString();
    }

    private String convertToKorean(final String word) {
        StringBuilder resultKor = new StringBuilder();
        int initialCode;
        int medialCode;
        int finalCode;
        int tempMedialCode;
        int tempFinalCode;
        int i = 0;
        while (i < word.length()) { // 숫자특수문자 처리
            if(ignoreChars.contains(word.substring(i, i + 1))){
                resultKor.append(word.charAt(i));
                i++;
                continue;
            }
            // 초성코드 추출
            initialCode = getCode(CodeType.CHOSUNG, word.substring(i, i + 1));
            i++;
            // 다음문자로
            // 중성코드 추출
            tempMedialCode = getDoubleMedial(i, word);
            // 두 자로 이루어진 중성코드 추출
            if (tempMedialCode != -1) {
                medialCode = tempMedialCode; i += 2;
            } else {
            // 없다면,
                medialCode = getSingleMedial(i, word);
            // 한 자로 이루어진 중성코드 추출
                i++;
            }
            // 종성코드 추출
            tempFinalCode = getDoubleFinal(i, word);
            // 두 자로 이루어진 종성코드 추출
            if (tempFinalCode != -1) {
                finalCode = tempFinalCode;
                // 그 다음의 중성 문자에 대한 코드를 추출한다.
                tempMedialCode = getSingleMedial(i + 2, word);
                if (tempMedialCode != -1) {
                // 코드 값이 있을 경우
                finalCode = getSingleFinal(i, word);
                // 종성 코드 값을 저장한다.
                } else {
                    i++;
                }
            } else {
                // 코드 값이 없을 경우 ,
                tempMedialCode = getSingleMedial(i + 1, word);
                // 그 다음의 중성 문자에 대한 코드 추출.
                if (tempMedialCode != -1) {
                    // 그 다음에 중성 문자가 존재할 경우,
                    finalCode = 0; // 종성 문자는 없음.
                    i--;
                }
                else {
                    finalCode = getSingleFinal(i, word);
                    // 종성 문자 추출
                    if (finalCode == -1){
                        finalCode = 0; i--;
                    // 초성,중성 + 숫자,특수문자,
                    //기호가 나오는 경우 index를 줄임.
                    }
                }
            }
            // 추출한 초성 문자 코드,
            //중성 문자 코드, 종성 문자 코드를 합한 후 변환하여 스트링버퍼에 넘김
            resultKor.append((char) (0xAC00 + initialCode + medialCode + finalCode));
            i++;
        }
        return resultKor.toString();
    }

    /**
     * 해당 문자에 따른 코드를 추출한다.
     * @param type
     * 초성 : chosung, 중성 : jungsung, 종성 : jongsung 구분
     * @param word 해당 문자 */
    private static int getCode(CodeType type, String word) {
        // 초성
        String init = "rRseEfaqQtTdwWczxvg";
        // 중성
        String[] mid = { "k", "o", "i", "O", "j", "p", "u", "P", "h", "hk", "ho", "hl", "y", "n", "nj", "np", "nl", "b", "m", "ml", "l" };
        // 종성
        String[] fin = { "r", "R", "rt", "s", "sw", "sg", "e", "f", "fr", "fa", "fq", "ft", "fx", "fv", "fg", "a", "q", "qt", "t", "T", "d", "w", "c", "z", "x", "v", "g" };
        switch (type) {
            case CHOSUNG:
                int index = init.indexOf(word);
                if (index != -1) { return index * 21 * 28; }
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
    private static int getSingleMedial(int i, String eng) {
        if ((i + 1) <= eng.length()) {
            return getCode(CodeType.JUNGSUNG, eng.substring(i, i + 1));
        } else {
            return -1;
        }
    }

    // 두 자로 된 중성을 체크하고, 있다면 값을 리턴한다.
    // 없으면 리턴값은 -1
    private static int getDoubleMedial(int i, String eng) {
        int result; if ((i + 2) > eng.length()) {
            return -1;
        } else {
            result = getCode(CodeType.JUNGSUNG, eng.substring(i, i + 2));
            return result;
        }
    }

    // 한 자로된 종성값을 리턴한다
    // 인덱스를 벗어낫다면 -1을 리턴
    private static int getSingleFinal(int i, String eng) {
        if ((i + 1) <= eng.length()) {
            return getCode(CodeType.JONGSUNG, eng.substring(i, i + 1));
        } else {
            return -1;
        }
    }

    // 두 자로된 종성을 체크하고, 있다면 값을 리턴한다.
    // 없으면 리턴값은 -1
    private static int getDoubleFinal(int i, String eng) {
            if ((i + 2) > eng.length()) {
                return -1;
            } else {
                return getCode(CodeType.JONGSUNG, eng.substring(i, i + 2));
            }
        }

    private static class UnicodeKorean {

        /* **********************************************
         * 알파벳으로 변환
         * 설연수 -> tjfdustn, 멍충 -> ajdcnd
         * **********************************************/
        /**
         * 초성 - 가(ㄱ), 날(ㄴ) 닭(ㄷ)
         */
        protected static final String[] arrChoSungEng = { "r", "R", "s", "e", "E",
                "f", "a", "q", "Q", "t", "T", "d", "w",
                "W", "c", "z", "x", "v", "g" };

        /**
         * 중성 - 가(ㅏ), 야(ㅑ), 뺨(ㅑ)
         */
        protected static final String[] arrJungSungEng = { "k", "o", "i", "O",
                "j", "p", "u", "P", "h", "hk", "ho", "hl",
                "y", "n", "nj", "np", "nl", "b", "m", "ml",
                "l" };

        /**
         * 종성 - 가(없음), 갈(ㄹ) 천(ㄴ)
         */
        protected static final String[] arrJongSungEng = { "", "r", "R", "rt",
                "s", "sw", "sg", "e", "f", "fr", "fa", "fq",
                "ft", "fx", "fv", "fg", "a", "q", "qt", "t",
                "T", "d", "w", "c", "z", "x", "v", "g" };

        /**
         * 단일 자음 - ㄱ,ㄴ,ㄷ,ㄹ... (ㄸ,ㅃ,ㅉ은 단일자음(초성)으로 쓰이지만 단일자음으론 안쓰임)
         */
        protected static String[] arrSingleJaumEng = { "r", "R", "rt",
                "s", "sw", "sg", "e", "E", "f", "fr", "fa", "fq",
                "ft", "fx", "fv", "fg", "a", "q", "Q", "qt", "t",
                "T", "d", "w", "W", "c", "z", "x", "v", "g" };
    }

}
