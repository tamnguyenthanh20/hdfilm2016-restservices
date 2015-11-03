package com.example.restservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by nttam on 10/20/2015.
 */
@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody  String welcomeToApiPage() {
        return "Rest Service API page";
    }

    @RequestMapping(value = "/test/1", method = RequestMethod.GET)
    public @ResponseBody long countEvent(@RequestParam(value = "x1")String x1,
                                         @RequestParam(value = "x2")String x2) {
        System.out.println("x1 = " + x1);
        long countEvent = 321;
        return countEvent;
    }

    @RequestMapping(value = "/test/2", method = RequestMethod.POST,  headers = "Content-Type=application/json")
    public ResponseEntity<Long> countEventUsingPost() {
        Long countEvent = new Long(123);
        return new ResponseEntity(countEvent, HttpStatus.OK);
    }

}
