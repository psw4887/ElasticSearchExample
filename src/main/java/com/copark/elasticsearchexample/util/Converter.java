package com.copark.elasticsearchexample.util;

import com.copark.elasticsearchexample.util.subutil.ConvertToEnglishUtil;
import com.copark.elasticsearchexample.util.subutil.ConvertToKoreanUtil;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Converter {
    enum CodeType {CHOSUNG, JUNGSUNG, JONGSUNG}
    ConvertToEnglishUtil convertToEnglishUtil = new ConvertToEnglishUtil();
    ConvertToKoreanUtil convertToKoreanUtil = new ConvertToKoreanUtil();

    static String ignoreChars = "`1234567890-=[]\\;',./~!@#$%^&*()_+{}|:\"<>? ";

    public String converter(final String word) {
        if (Pattern.matches("^[a-zA-Z]*$", word)) {
            return convertToKorean(word);
        }
        return convertToEnglish(word);
    }

    private String convertToEnglish(final String word) {
        StringBuilder resultEng = new StringBuilder();
        int i = 0;

        while (i < word.length()) {
            if (!ignoreChars.contains(word.substring(i, i + 1))) {/*  한글자씩 읽어들인다. */
                char chars = (char) (word.charAt(i) - 0xAC00);

                /* A. 자음과 모음이 합쳐진 글자인경우 */
                if (chars <= 11172) {
                    /* A-1. 초/중/종성 분리 */
                    wordSeparation(resultEng, chars);

                } else {
                    /* B. 한글이 아니거나 자음만 있을경우 */

                    /* 알파벳으로 */
                    if (chars >= 34097 && chars <= 34126) {
                        /* 단일자음인 경우 */
                        int jaum = (chars - 34097);
                        resultEng.append(convertToEnglishUtil.getArrSingleJaumEng()[jaum]);
                    } else if (chars >= 34127 && chars <= 34147) {
                        /* 단일모음인 경우 */
                        int moum = (chars - 34127);
                        resultEng.append(convertToEnglishUtil.getArrJungSungEng()[moum]);
                    } else {
                        /* 알파벳인 경우 */
                        resultEng.append(((char) (chars + 0xAC00)));
                    }
                }
            } else {
                resultEng.append(word.charAt(i));
            }
            i++;
        }
        return resultEng.toString();
    }

    private void wordSeparation(StringBuilder resultEng, char chars) {
        int chosung = chars / (21 * 28);
        int jungsung = chars % (21 * 28) / 28;
        int jongsung = chars % (21 * 28) % 28;

        /* 알파벳으로 */
        resultEng.append(convertToEnglishUtil.getArrChoSungEng()[chosung])
                 .append(convertToEnglishUtil.getArrJungSungEng()[jungsung]);
        if (jongsung != 0x0000) {
            /* A-3. 종성이 존재할경우 result 에 담는다 */
            resultEng.append(convertToEnglishUtil.getArrJongSungEng()[jongsung]);
        }
    }

    private String convertToKorean(final String word) {
        StringBuilder resultKor = new StringBuilder();
        int i = 0;
        int initialCode;
        int medialCode;
        int finalCode;
        int tempMedialCode;
        int tempFinalCode;
        while (i < word.length()) { // 숫자특수문자 처리
            if (ignoreChars.contains(word.substring(i, i + 1))) {
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
                medialCode = tempMedialCode;
                i += 2;
            } else {
                medialCode = getSingleMedial(i, word);
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
                } else {
                    finalCode = getSingleFinal(i, word);
                    // 종성 문자 추출
                    if (finalCode == -1) {
                        finalCode = 0;
                        i--;
                        // 초성,중성 + 숫자,특수문자,
                        // 기호가 나오는 경우 index 를 줄임.
                    }
                }
            }
            // 추출한 초성 문자 코드,
            // 중성 문자 코드, 종성 문자 코드를 합한 후 변환하여 스트링버퍼에 넘김
            resultKor.append((char) (0xAC00 + initialCode + medialCode + finalCode));
            i++;
        }
        return resultKor.toString();
    }

}
