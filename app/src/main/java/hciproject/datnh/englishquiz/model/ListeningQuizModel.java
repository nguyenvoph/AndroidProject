package hciproject.datnh.englishquiz.model;

import hciproject.datnh.englishquiz.entity.ListeningQuizEntity;

import java.util.List;

public class ListeningQuizModel {
    private int difficult;
    private int quantity;
    private List<ListeningQuizEntity> questions;

    public ListeningQuizModel() {
    }

    public ListeningQuizModel(int difficult, int quantity, List<ListeningQuizEntity> questions) {
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

    public List<ListeningQuizEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ListeningQuizEntity> questions) {
        this.questions = questions;
    }
}
