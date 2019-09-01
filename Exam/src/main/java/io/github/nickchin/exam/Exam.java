/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.nickchin.exam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author nickc
 */
public class Exam implements XMLize {
    private final List<Question> QUESTIONS;
    private final String ID;

    /**
     *
     * @param id string of the exam name
     */
    Exam(String id) {
        this.ID = id;
        this.QUESTIONS = new ArrayList<>();
    }

    /**
     * @param question any instance of a question
     */
    void addQuestion(Question question) {
        // add input questions to list of questions
        this.QUESTIONS.add(question);
    }

    /**
     *
     * @return a string format of the exam and questions that it has
     */
    public String printExam() {
        // exam title
        String str = this.ID + "\n\n";
        
        // iterate through questions and call their 
        // method for printing questions
        for (int i = 0; i < this.QUESTIONS.size(); i++) {
            str += (i + 1) + ") " + this.QUESTIONS.get(i).printQuestion()
                    + "\n\n";
        }
        return str;
    }

    /**
     * takesExam() will read user input for each question and record the answers
     * for each question
     */
    public void takeExam() {
        // initialize user input
        Scanner input = new Scanner(System.in);
        
        // iterate over questions
        for (Question q : this.QUESTIONS) {
            // print question
            System.out.println(q.printQuestion());
            System.out.println("\n Enter your answer here (press enter "
                    + "to confirm):");
            
            // record user answer
            String ans = input.nextLine();
            
            // store answer in question
            q.answer(ans);
            System.out.println();
        }
        System.out.println("You have completed the exam");
    }

    /**
     *
     * @return a string of the exam with the user answers under each question
     */
    public String printExamWithAnswers() {
        // exam title
        String str = this.ID + "\n\n";
        
        // iterate over questions
        for (int i = 0; i < this.QUESTIONS.size(); i++) {
            // print using questions printQuestion() method
            str += (i + 1) + ") " + this.QUESTIONS.get(i).printQuestion()
                    + "\n";
            
            // print user answers if inputted
            str += "User answer: " + this.QUESTIONS.get(i).getUserAnswer()
                    + "\n\n";
        }
        return str;
    }

    /**
     *
     * @param e an Exam
     * @return this will return true if both exams have the same id, same number
     * of questions, and same questions using the question comparison, false
     * otherwise
     */
    public boolean equals(Exam e) {
        // checks to see if exam name is the same
        boolean test = this.ID.equals(e.ID);
        
        // checks to see if number of questions are the same
        if (QUESTIONS.size() == e.QUESTIONS.size()) {
            // iterate over questions
            for (int i = 0; i < QUESTIONS.size(); i++) {
                // check to see if questions are the same and update test
                test = test && QUESTIONS.get(i).equals(e.QUESTIONS.get(i));
            }
            return test;
        }
        return false;
    }

    /**
     *
     * @return string in XML format of the exam with questions
     */
    @Override
    public String getXML() {
        String str = "<exam id = \"" + this.ID + "\">\n";
        for (Question q : QUESTIONS) {
            // getXML of each question by iterating
            str += ((XMLize) q).getXML() + "\n";
        }
        str += "</exam>";
        return str;
    }

    /**
     * 
     * @param filename filepath as a string of where to save XML file
     */
    void saveXML(String filename) {
        String str = this.getXML();
        try {
            Files.write(Paths.get(filename), str.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(Exam.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     * @param filename File path of where XML file is
     * @return an Exam with contents based on XML file
     * @throws Exception if file could not be parsed, will catch other system
     * exceptions
     */
    public static Exam fromXML(String filename) throws Exception {

        try {
            // read in XML file and parse it as a document
            File xmlFile = new File(filename);
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // get the exam title
            String examID = doc.getDocumentElement().getAttribute("id");
            
            // begin building exam
            Exam exam = new Exam(examID);

            // get questions in exam
            NodeList questionList = doc.getElementsByTagName("question");

            // iterate through questions
            for (int i = 0; i < questionList.getLength(); i++) {
                Node question = questionList.item(i);
                
                if (question.getNodeType() == Node.ELEMENT_NODE) {
                    Element questionElement = (Element) question;
                    
                    // check the question type
                    String questionType = questionElement.getAttribute("type");

                    // if question is an MCQ
                    if (questionType.equals("mcq")) {
                        String qsn = questionElement.getAttribute("qsn");
                        String correctAnswer
                                = questionElement.getAttribute("correctanswer");
                        
                        // create base MCQ
                        MCQuestion mcq = new MCQuestion(qsn,
                                correctAnswer.charAt(0));

                        // look through the options of the MCQ
                        if (questionElement.hasChildNodes()) {
                            // grab options as a child of MCQ
                            NodeList optionList = question.getChildNodes();
                            
                            //iterate through options
                            for (int j = 0; j < optionList.getLength(); j++) {
                                Node option = optionList.item(j);
                                if (option.getNodeType() == Node.ELEMENT_NODE) {
                                    Element opElement = (Element) option;
                                    
                                    // create option for MCQ based on 
                                    // text content
                                    mcq.addOption(opElement.getTextContent());
                                }
                            }
                        }
                        // add MCQ to exam
                        exam.addQuestion(mcq);
                        
                    } else if (questionType.equals("tfq")) {
                        // if question is T/F question
                        String qsn = questionElement.getAttribute("qsn");
                        String correctAnswer
                                = questionElement.getAttribute("correctanswer");
                        TrueFalseQuestion tfq = new TrueFalseQuestion(qsn,
                                correctAnswer);
                        
                        // add question to exam
                        exam.addQuestion(tfq);
                    } else if (questionType.equals("oaq")) {
                        // if question is open answer question
                        String qsn = questionElement.getAttribute("qsn");
                        String correctAnswer
                                = questionElement.getAttribute("correctanswer");
                        OpenAnswerQuestion oaq = new OpenAnswerQuestion(qsn,
                                correctAnswer);
                        
                        // add question to exam
                        exam.addQuestion(oaq);
                    }
                }
            }
            // return exam
            return exam;

            // catch possible exception
        } catch (SAXException ex) {
            Logger.getLogger(Exam.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Exam.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Exam.class.getName()).log(Level.SEVERE, null, ex);
        }

        // throws an exception if the exam couldn't be parsed
        // instead of returning an empty exam
        throw new Exception("couldn't parse xml");
    }
}
