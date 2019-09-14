package hciproject.datnh.englishquiz.model;

import hciproject.datnh.englishquiz.entity.MultipleChoiceQuizEntity;

import java.util.List;

public class MultipleChoiceQuizModel {
    private int difficult;
    private int quantity;
    private List<MultipleChoiceQuizEntity> questions;

    public MultipleChoiceQuizModel() {
    }

    public MultipleChoiceQuizModel(int difficult, int quantity, List<MultipleChoiceQuizEntity> questions) {
        this.difficult = difficult;
        this.quantity = quantity;
        this.questions = questions;
    }

    public int getDifficult() {
        return difficult;
    }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<MultipleChoiceQuizEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<MultipleChoiceQuizEntity> questions) {
        this.questions = questions;
    }
}
