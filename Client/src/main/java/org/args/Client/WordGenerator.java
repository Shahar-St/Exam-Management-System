package org.args.Client;

import LightEntities.LightExam;
import LightEntities.LightQuestion;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class WordGenerator {

    private final int fontSize = 14;

    public void createWord(LightExam exam) throws IOException {
        XWPFDocument document = new XWPFDocument();
        FileOutputStream out = new FileOutputStream(new File(exam.getId() + "_" + "exam" + ".docx"));
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setText(exam.getTitle());
        titleRun.addCarriageReturn();
        titleRun.setFontSize(fontSize);
        titleRun.setBold(true);
        titleRun.setUnderline(UnderlinePatterns.SINGLE);
        XWPFParagraph info = document.createParagraph();
        XWPFRun infoRun =info.createRun();
        infoRun.setText("Notes: "+exam.getStudentNotes());
        infoRun.addCarriageReturn();
        infoRun.addCarriageReturn();
        infoRun.setText("Duration: "+exam.getDurationInMinutes());
        infoRun.setFontSize(fontSize);
        infoRun.setBold(true);
        infoRun.setUnderline(UnderlinePatterns.SINGLE);
        infoRun.addCarriageReturn();
        infoRun.addCarriageReturn();
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun paragraphRun = paragraph.createRun();
        paragraphRun.setFontSize(fontSize);
        for(LightQuestion lightQuestion:exam.getLightQuestionList()){
            int index = exam.getLightQuestionList().indexOf(lightQuestion);
            paragraphRun.setText("("+(index+1)+") "+lightQuestion.getQuestionContent());
            paragraphRun.addCarriageReturn();
            paragraphRun.addCarriageReturn();
            paragraphRun.setText("(a) "+lightQuestion.getAnswers().get(0));
            paragraphRun.addCarriageReturn();
            paragraphRun.setText("(b) "+lightQuestion.getAnswers().get(1));
            paragraphRun.addCarriageReturn();
            paragraphRun.setText("(c) "+lightQuestion.getAnswers().get(2));
            paragraphRun.addCarriageReturn();
            paragraphRun.setText("(d) "+lightQuestion.getAnswers().get(3));
            paragraphRun.addCarriageReturn();
            paragraphRun.addCarriageReturn();
            paragraphRun.setText("Question Score: "+exam.getQuestionsScores().get(index));
            paragraphRun.addCarriageReturn();
            paragraphRun.addCarriageReturn();
            paragraphRun.addCarriageReturn();
        }
        document.write(out);
        out.close();
        document.close();

    }
}
