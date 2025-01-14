package com.dengue_webapp.dengue_webapp.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/phi")
public class PHIController {

    @PostMapping
    public String loginPHIOfficer(){
        return "Login success!";
    }

    @GetMapping("/{id}")
    public String getPHIOfficerInfo(@PathVariable String id){
        return "PHI Officer Info for " + id;
    }

    @GetMapping(path = "/list", params = {"searchText", "page", "size"})
    public String getAllPHIOfficerInfo(@RequestParam String searchText, @RequestParam int page, @RequestParam int size){
        return "All PHI Officer Info List for " + searchText + "" + page + "" + size + "";
    }

    @PutMapping(path = "/{id}")
    public String updatePHIOfficerInfo(@PathVariable String id){
        return "Update PHI Officer Info for " + id;
    }

    @DeleteMapping(path = "/{id}")
    public String deletePHIOfficerInfo(@PathVariable String id){
        return "Delete PHI Officer Info for " + id;
    }


}
