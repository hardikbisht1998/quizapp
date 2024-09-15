package com.telesko.quizapp.service;


import com.telesko.quizapp.dao.QuestionDao;
import com.telesko.quizapp.dao.QuizDao;
import com.telesko.quizapp.model.Question;
import com.telesko.quizapp.model.QuestionWrapper;
import com.telesko.quizapp.model.Quiz;
import com.telesko.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category,int numQ, String title){
        List<Question> questions=questionDao.findRandomQuestionsByCategory(category,numQ);
        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
    }


    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Integer id) {
        Optional<Quiz> quiz=quizDao.findById(id);
        List<Question> questionsFromDB=quiz.get().getQuestions();
        List<QuestionWrapper> questionForUser=new ArrayList<>();

        for(Question q:questionsFromDB){
            QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionForUser.add(qw);

        }
        return new ResponseEntity<>(questionForUser,HttpStatus.OK);


    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Optional<Quiz> quiz= quizDao.findById(id);
        List<Question> questionsFromDB=quiz.get().getQuestions();
        int counter=0;
        int i=0;
        for(Question q: questionsFromDB){

            if(q.getRightAnswer().equals(responses.get(i++).getResponse())){
                counter++;
            }
        }
            return new ResponseEntity<>(counter,HttpStatus.OK);
    }
}
