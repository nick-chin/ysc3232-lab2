/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.nickchin.exam;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nickc
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        // create an exam
        Exam exam = new Exam("demo exam");

        // create mcq with 4 different choices
        MCQuestion mcq1 = new MCQuestion("What is the value of pi?", 'A');
        mcq1.addOption("3.14");
        mcq1.addOption("1.32");
        mcq1.addOption("2.28");
        mcq1.addOption("65.5");

        // create a t/f question
        TrueFalseQuestion tfq = new TrueFalseQuestion("Is there a an answer "
                + "to this quesition?", "True");

        // create an open answer question
        OpenAnswerQuestion oaq = new OpenAnswerQuestion("Why does the sun set "
                + "everyday?", "The sun sets because the Earth rotates.");

        // create another mcq
        MCQuestion mcq2 = new MCQuestion("Which of the "
                + "following is a primate?", 'C');
        mcq2.addOption("Starfish");
        mcq2.addOption("Dogs");
        mcq2.addOption("Apes");

        // add all questions into the exam
        exam.addQuestion(mcq1);
        exam.addQuestion(tfq);
        exam.addQuestion(oaq);
        exam.addQuestion(mcq2);

        // take the exam
        exam.takeExam();

        // prints exam with answers inputted
        System.out.println(exam.printExamWithAnswers());

        // save exam to xml
        System.out.println("saving exam as XML...");
        exam.saveXML("test.txt");

        try {
            // read exam via xml file
            System.out.println("reading exam from xml...");
            Exam e = Exam.fromXML("test.txt");

            // test that the exams are equivalent
            System.out.println("Is the memeory exam the "
                    + "same as the parsed exam? " + exam.equals(e));
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
