package org.args.Client;

import LightEntities.LightExam;
import LightEntities.LightQuestion;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WordGenerator {

    /**
     * the method gets lightexam for execution and generates a word file contains the exam data.
     **/
    public void createWordFile(LightExam exam, File filePath) throws IOException {
        XWPFDocument document = new XWPFDocument();
        FileOutputStream out = new FileOutputStream(filePath);
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setText(exam.getTitle());
        titleRun.addCarriageReturn();
        int fontSize = 14;
        titleRun.setFontSize(fontSize);
        titleRun.setBold(true);
        titleRun.setUnderline(UnderlinePatterns.SINGLE);
        XWPFParagraph info = document.createParagraph();
        XWPFRun infoRun = info.createRun();
        infoRun.setText("Notes: " + exam.getStudentNotes());
        infoRun.addCarriageReturn();
        infoRun.addCarriageReturn();
        infoRun.setText("Duration: " + exam.getDurationInMinutes());
        infoRun.setFontSize(fontSize);
        infoRun.setBold(true);
        infoRun.setUnderline(UnderlinePatterns.SINGLE);
        infoRun.addCarriageReturn();
        infoRun.addCarriageReturn();
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun paragraphRun = paragraph.createRun();
        paragraphRun.setFontSize(fontSize);
        for (LightQuestion lightQuestion : exam.getLightQuestionList()) {
            int index = exam.getLightQuestionList().indexOf(lightQuestion);
            paragraphRun.setText("(" + (index + 1) + ") " + lightQuestion.getQuestionContent());
            paragraphRun.addCarriageReturn();
            paragraphRun.addCarriageReturn();
            paragraphRun.setText("(a) " + lightQuestion.getAnswers().get(0));
            paragraphRun.addCarriageReturn();
            paragraphRun.setText("(b) " + lightQuestion.getAnswers().get(1));
            paragraphRun.addCarriageReturn();
            paragraphRun.setText("(c) " + lightQuestion.getAnswers().get(2));
            paragraphRun.addCarriageReturn();
            paragraphRun.setText("(d) " + lightQuestion.getAnswers().get(3));
            paragraphRun.addCarriageReturn();
            paragraphRun.addCarriageReturn();
            paragraphRun.setText("Question Score: " + exam.getQuestionsScores().get(index));
            paragraphRun.addCarriageReturn();
            paragraphRun.addCarriageReturn();
            paragraphRun.addCarriageReturn();
        }
        document.write(out);
        out.close();
        document.close();
    }

    /**
     * method get byte array and file path , writes the byte array to docx file located in filepath.
     **/
    public void saveWordFile(byte[] source, File filePath) throws IOException {
        // handle non existing source bytes
        if(source == null)
            return;
        FileOutputStream outputStream = new FileOutputStream(filePath);
        outputStream.write(source);
        outputStream.close();

    }
}
