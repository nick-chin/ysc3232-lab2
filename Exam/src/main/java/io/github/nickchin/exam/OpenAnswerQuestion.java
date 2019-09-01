/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.nickchin.exam;

/**
 *
 * @author nickc
 */
public class OpenAnswerQuestion implements Question, XMLize{
    private final String QUESTION;
    private final String CORRECTANSWER;
    private String userAnswer;
    
    /**
     * 
     * @param question string of what the open answer question is asking
     * @param correctAnswer string of the question's answer
     */
    OpenAnswerQuestion(String question, String correctAnswer) {
        this.QUESTION = question;
        this.CORRECTANSWER = correctAnswer;
    }
    
    /**
     * 
     * @returns formatted string of the question
     */
    @Override
    public String printQuestion() {
        return this.QUESTION;
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
        // check to see if the questions are the same type
        if (q instanceof OpenAnswerQuestion) {
            // checks to see if the question and the answer are the same
            return this.QUESTION.equals(((OpenAnswerQuestion) q).QUESTION) && 
                    this.CORRECTANSWER.equals(
                            ((OpenAnswerQuestion)q).CORRECTANSWER);
        }
        return false;
    } 
    
    
    /**
     * 
     * @return string in XML format of the question
     */
    @Override
    public String getXML() {
        return "<question type = \"oaq\" qsn = \"" + this.QUESTION + 
                "\" correctanswer = \"" + this.CORRECTANSWER + "\"/>";
    }
}
