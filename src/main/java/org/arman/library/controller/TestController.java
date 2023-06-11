package org.arman.library.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {
    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return new ResponseEntity<String>("Hello, fellas!", HttpStatus.OK);
    }
}
