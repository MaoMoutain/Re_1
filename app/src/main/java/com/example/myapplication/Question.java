package com.example.myapplication;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

//保存从数据库中读取的记录
public class Question implements Serializable {
    private static final long serialVersionUID = 1L;
    public int ID;
    public int tid;
    public String question;
    public String answerA;
    public String answerB;
    public String answerC;
    public String answerD;
    public String answer;
    public String Translation;
    public String explaination;
    public  int year;
    public int done;
    public int wrong;
    public int collect;
    //用户选择的答案
    public String selectedAnswer="NULL";

    public int getID() {
        return ID;
    }

    public int getWrong() {
        return wrong;
    }

    public int getDone() {
        return done;
    }

    public int getYear() {
        return year;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAnswer() {
        return answer;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public String getExplaination() {
        return explaination;
    }

    public String getQuestion() {
        return question;
    }

    public String getTranslation() {
        return Translation;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public void setExplaination(String explaination) {
        this.explaination = explaination;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public void setTranslation(String translation) {
        Translation = translation;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}