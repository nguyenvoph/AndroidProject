package hciproject.datnh.englishquiz.model;

import hciproject.datnh.englishquiz.entity.WordQuizEntity;

import java.util.List;

public class WordQuizModel {
    private List<WordQuizEntity> questions;

    public WordQuizModel() {
    }

    public WordQuizModel(int difficult, int quantity, List<WordQuizEntity> questions) {
        this.questions = questions;
    }

    public List<WordQuizEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<WordQuizEntity> questions) {
        this.questions = questions;
    }
}
