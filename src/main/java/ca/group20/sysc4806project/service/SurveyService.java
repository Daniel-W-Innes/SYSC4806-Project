package ca.group20.sysc4806project.service;

import ca.group20.sysc4806project.model.Survey;

public interface SurveyService {
    Survey saveSurvey(Survey survey);

    Survey getSurvey(String name);
}