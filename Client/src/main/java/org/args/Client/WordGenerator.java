package org.args.Client;

import LightEntities.LightExam;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class WordGenerator {

    public void createWord(LightExam exam) throws IOException {
        XWPFDocument document = new XWPFDocument();
        FileOutputStream out = new FileOutputStream(new File(exam.getId() + "_" + "exam" + ".docx"));
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("Title: "+exam.getTitle()+"\n"+"Student Notes: "+exam.getStudentNotes()+"\n"+"Exam Duration: "+exam.getDurationInMinutes()+"\n");
        document.write(out);
        out.close();
        document.close();

    }
}
