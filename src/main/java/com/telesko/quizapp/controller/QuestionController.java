package com.telesko.quizapp.controller;


import com.telesko.quizapp.model.Question;
import com.telesko.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestion")
    public ResponseEntity<List<Question>> getAllQuestion(){

        return questionService.getAllQuestion();
    }


    @GetMapping("category/{cat}")
    public ResponseEntity<List<Question>> getOuestionByCategory(@PathVariable("cat") String category){
        return questionService.getByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);

    }
}
