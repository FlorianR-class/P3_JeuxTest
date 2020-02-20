package fr.ribotin.test.model;

import java.util.List;

public class Question {

    private String mQuestion;
    private List<String> mChoiceList;
    private int mAnswerIndex;

    public Question(String question, List<String> choiceList, int answerIndex) {
        this.setQuestion(question);
        this.setChoiceList(choiceList);
        this.setAnswerIndex(answerIndex);
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        //TODO Si ma liste est null alors il declenche une erreur "Question cannot be null" !!
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        mQuestion = question;
    }

    public List<String> getChoiceList() {
        return mChoiceList;
    }

    public void setChoiceList(List<String> choiceList) {
        //TODO Si ma liste est null alors il declenche une erreur "Array cannot be null" !!
        if (choiceList == null){
            throw new IllegalArgumentException("Array cannot be null");
        }
        mChoiceList = choiceList;
    }

    public int getAnswerIndex() {
        return mAnswerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        //TODO Si l'Index de la reponse est inferieur a 0 ou que l'index de la reponse est Superieur a la taille de la List de response alors il me declenche une erreur "Answer Index is out of bound" !!
        if (answerIndex < 0 || answerIndex > mChoiceList.size()){
            throw new IllegalArgumentException("Answer Index is out of bound");
        }
        mAnswerIndex = answerIndex;
    }
}
