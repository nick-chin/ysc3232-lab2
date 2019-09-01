/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.nickchin.exam;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nickc
 */
public class MCQuestion implements Question, XMLize{
    private final String QUESTION;
    private final char CORRECTANSWER;
    private String userAnswer;
    private List<String> options;
    
    
    /**
     * 
     * @param question a string of what the MCQ is asking
     * @param answer a character that determines the question's correct answer
     * of the letters A-D, where A refers to the first of up to 4 options
     */
    MCQuestion(String question, char correctAnswer) {
        this.QUESTION = question;
        this.CORRECTANSWER = correctAnswer;
        this.options = new ArrayList<>();
    }
    
    /**
     * 
     * @param option a string of the possible option for the MCQ, will print
     * a warning if there are already 4 options.
     */
    void addOption(String option) {
        if (this.options.size() == 4) {
            System.out.println("Too many options already");
        } else {
            this.options.add(option);
        }
    }
    
    /**
     * 
     * @param ans string representation of the user's answer 
     * that will be stored. Calling this method again will override
     * the previous answer.
     */
    @Override
    public void answer(String ans) {
        this.userAnswer = ans;
    }
    
    /**
     * 
     * @return formatted string of the question with its possible choices
     */
    @Override
    public String printQuestion() {
        String str = this.QUESTION + "\n";
        for (int i = 0; i < this.options.size(); i++) {
            char letter = (char)(65 + i);
            str += Character.toString(letter) + ". " + this.options.get(i);
            if (i < this.options.size() - 1) 
                str += "\n";
        }
        return str;
    }
    
    /**
     * 
     * @return the user inputted answer
     */
    @Override
    public String getUserAnswer() {
        return this.userAnswer;
    }
    
    /**
     * 
     * @param q any Question
     * @return true if questions types are the same, question strings
     * are equal, and question correct answer are equal, false otherwise
     */
    @Override
    public boolean equals(Question q) {
        if (q instanceof MCQuestion) {
            // check to see if mcq questions have the same amount of options
            if (options.size() == ((MCQuestion) q).options.size()) {
                
                // check that the questions are the same and answer is the same
                boolean test = this.QUESTION.equals(
                        ((MCQuestion) q).QUESTION) && 
                    this.CORRECTANSWER == ((MCQuestion)q).CORRECTANSWER;
                
                // iterate through options and check that they are the same
                for (int i = 0; i < options.size(); i++) {
                    test = test && options.get(i)
                            .equals(((MCQuestion) q).options.get(i));
                }
                
            return test;
            }
        }
        return false;
    } 
    
    /**
     * 
     * @return string in XML format of the question
     */
    @Override
    public String getXML() {
        String str = "<question type = \"mcq\" qsn = \"" + this.QUESTION + 
                "\" correctanswer = \"" + this.CORRECTANSWER + "\">\n";
        for (String op : options) {
            str += "<option>" + op + "</option>\n";
        }
        str += "</question>";
        return str;
    }
}
