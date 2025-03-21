package com.springboot.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response {
    private int status; // 0 || 1 => success || fail
    private String message; // 0 => "Success" || 1 => error form server
    private Object data; // 0 => != null || 1 => == null
}
