package com.connect.ConnectSphere.Controller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AuthResponse {
    private String token;
    private String message;



}

//package com.connect.ConnectSphere.Controller;
//
//public class AuthResponse {
//    private String token;
//    private String message;
//
//    // Default constructor
//    public AuthResponse() {}
//
//    // Parameterized constructor
//    public AuthResponse(String token, String message) {
//        this.token = token;
//        this.message = message;
//    }
//
//    // Getters and Setters
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//}
