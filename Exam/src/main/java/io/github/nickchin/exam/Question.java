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
public interface Question {
    /**
     * 
     * @param ans a string that is the user's input for the answer
     * to the question
     */
    public void answer(String ans);
    
    /**
     * 
     * @return a string format of the question
     */
    String printQuestion();
    
    /**
     * 
     * @return the user inputted answer
     */
    public String getUserAnswer();
    
    /**
     * 
     * @param q any Question
     * @return true if questions are equivalent, false otherwise; equivalence
     * is defined individually by each question
     */
    public boolean equals(Question q);
            
}
