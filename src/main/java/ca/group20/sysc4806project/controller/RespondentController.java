package ca.group20.sysc4806project.controller;

import ca.group20.sysc4806project.model.Respondent;
import ca.group20.sysc4806project.model.Survey;
import ca.group20.sysc4806project.model.answer.Answer;
import ca.group20.sysc4806project.model.question.Question;
import ca.group20.sysc4806project.repository.RespondentRepo;
import ca.group20.sysc4806project.service.AnswerService;
import ca.group20.sysc4806project.service.RespondentService;
import ca.group20.sysc4806project.service.SurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v0/respondents")
@RequiredArgsConstructor
@Slf4j
public class RespondentController {

    private final AnswerService answerService;
    private final RespondentService respondentService;
    private final SurveyService surveyService;

    @PostMapping("/answer/{respondentID}")
    public ResponseEntity<?> createAnswer(@PathVariable("respondentID") long respondentID,
                                        @Valid @RequestBody Answer answer) {
        Answer newAnswer = answerService.saveAnswer(answer);
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/api/v0/respondents")
                        .toUriString());
        return ResponseEntity.created(uri).body(newAnswer);
    }


    @PostMapping("/answer/{respondentID}")
    public ResponseEntity<?> createRespondentAnswer(@PathVariable("respondentID") long respondentID,
                                                    @Valid @RequestBody Answer answer) {
        Answer newAnswer = answerService.saveAnswer(answer);
        Respondent respondent = respondentService.findRespondentById(respondentID);
        respondent.addAnswer(newAnswer);
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/api/v0/respondentAnswer")
                        .toUriString());
        return ResponseEntity.created(uri).body(newAnswer);
    }

    @PostMapping("/respondent/{surveyId}")
    public ResponseEntity<?> createRespondent(@PathVariable("surveyId") long surveyId,
                                              @Valid @RequestBody Respondent respondent){
        try {
            Survey survey = surveyService.findSurveyById(surveyId);
            respondent.setSurvey(survey);
            survey.addRespondents(respondent);
            //Answer newAnswer = answerService.saveAnswer(answer);
            //respondent.addAnswer(newAnswer);
            Respondent newRespondent = respondentService.saveRespondent(respondent);
            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/api/v0/respondents/" + surveyId)
                            .toUriString());
            return ResponseEntity.created(uri).body(newRespondent);
        } catch (Exception e) { // add new Exception for Survey already exists or survey already added
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
