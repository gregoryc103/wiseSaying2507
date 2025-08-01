package com.ll;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Rq {
    private final String actionName;
    private final Map<String, String> paramsMap;

    public Rq(String cmd) {

        // stream() 적용 전 최초 작성 코드
        /*        paramsMap = new HashMap<>();

        String[] cmdBits = cmd.split("\\?", 2);
        actionName = cmdBits[0];

        String queryString = cmdBits.length > 1 ? cmdBits[1].trim() : "";

        String[] queryStringBits = queryString.split("&");

        for (String queryParam : queryStringBits) {
            String[] queryParamBits = queryParam.split("=", 2);
            String key = queryParamBits[0].trim();
            String value = queryParamBits.length > 1 ? queryParamBits[1].trim() : "";

            if (value.isEmpty()) {
                continue;
            }

            paramsMap.put(key, value);
        }*/

        //stream() 적용 ver1
        String[] cmdBits = cmd.split("\\?", 2);
        actionName = cmdBits[0];

        String queryString = cmdBits.length > 1 ? cmdBits[1].trim() : "";

        if (queryString.isEmpty()) {
            paramsMap = new HashMap<>();
            return;
        }

        paramsMap = Arrays.stream(queryString.split("&"))
                .map(queryParam -> queryParam.split("=", 2))
                .filter(queryParamBits -> queryParamBits.length > 1 && !queryParamBits[1].trim().isEmpty())
                .collect(Collectors.toMap(
                        bits -> bits[0].trim(),
                        bits -> bits[1].trim()
                ));
        // stream() 적용 ver2
        /*
        String[] cmdBits = cmd.split("\\?", 2);
        actionName = cmdBits[0];

        paramsMap = cmdBits.length > 1 && !cmdBits[1].trim().isEmpty()
                ? Arrays.stream(cmdBits[1].trim().split("&"))
                .map(param -> param.split("=", 2))
                .filter(parts -> parts.length == 2 && !parts[1].trim().isEmpty())
                .collect(Collectors.toMap(
                        parts -> parts[0].trim(),
                        parts -> parts[1].trim(),
                        (v1, v2) -> v2,  // 중복 키 처리
                        HashMap::new
                ))
                : new HashMap<>();*/

        // stream() 적용 ver3
        /*String[] cmdBits = cmd.split("\\?", 2);
        actionName = cmdBits[0];

        paramsMap = Stream.of(cmdBits)
                .skip(1)  // 첫 번째 요소(actionName) 건너뛰기
                .filter(s -> !s.trim().isEmpty())
                .flatMap(queryString -> Arrays.stream(queryString.trim().split("&")))
                .map(param -> param.split("=", 2))
                .filter(parts -> parts.length == 2 && !parts[1].trim().isEmpty())
                .collect(Collectors.toMap(
                        parts -> parts[0].trim(),
                        parts -> parts[1].trim(),
                        (v1, v2) -> v2,
                        HashMap::new
                ));*/
    }
    public String getActionName() {
        return actionName;
    }

    public String getParam(String paramName, String defaultValue) {
        if (paramsMap.containsKey(paramName)) {
            return paramsMap.get(paramName);
        } else {
            return defaultValue;
        }
    }

    public int getParamAsInt(String paramName, int defaultValue) {
        String value = getParam(paramName, "");

        if (value.isEmpty()) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}