package com.mangkyu.stream.Quiz1;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Quiz1 {

    // 1.1 각 취미를 선호하는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz1() throws IOException {
        List<String[]> csvLines = readCsvLines();

        // 1. white space 제거
        // 2. : 기준으로 토큰으로 분해 -> flatMap: 중첩 구조의 차원을 한단계 낮춘 후 스트림 형태로 반환
        // 3. Collectors.toMap(keyMapper, valueMapper, mergeFunction) -> mergeFunction: key값의 collision이 일어났을때, oldValue, newValue의 처리를 하는 함수
        return csvLines.stream()
            .map(csvLine -> csvLine[1].replaceAll("\\s", ""))
            .flatMap(hobbies -> Arrays.stream(hobbies.split(":")))
            .collect(Collectors.toMap(hobby->hobby, hobby->1, Integer::sum));
    }

    // 1.2 각 취미를 선호하는 정씨 성을 갖는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz2() throws IOException {
        List<String[]> csvLines = readCsvLines();

        // 정씨 성을 갖는 인원을 기준으로 먼저 필터링한 stream을 기준으로, 1.1과 동일 연산 수행
        return csvLines.stream()
            .filter(c->c[0].startsWith("정"))
            .map(c->c[1].replaceAll("\\s", ""))
            .flatMap(c->Arrays.stream(c.split(":")))
            .collect(Collectors.toMap(hobby->hobby, hobby->1, Integer::sum));
    }

    // 1.3 소개 내용에 '좋아'가 몇번 등장하는지 계산하여라.
    public int quiz3() throws IOException {
        List<String[]> csvLines = readCsvLines();
        return csvLines.stream().mapToInt(c->getCount(c[2], 0)).sum();
    }

    private int getCount(String target, int startIndex) {
        int idx = target.indexOf("좋아", startIndex);
        if(idx>=0) {
            return 1 + getCount(target, idx + 2);
        }
        return 0;
    }

    private List<String[]> readCsvLines() throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader(getClass().getResource("/user.csv").getFile()));
        csvReader.readNext();
        return csvReader.readAll();
    }

}
