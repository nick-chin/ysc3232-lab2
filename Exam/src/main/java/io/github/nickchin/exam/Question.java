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
    void answer(String ans);
    String printQuestion();
}