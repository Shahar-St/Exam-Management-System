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
        //Blank Document
        XWPFDocument document = new XWPFDocument();
        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(
                new File(exam.getId() + "_" + "exam" + ".docx"));
        //create Paragraph
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("Title: "+exam.getTitle()+"\n"+"");
        //Close document
        out.close();
        System.out.println("createdWord" + "_"  + ".docx" + " written successfully");
        document.write(out);
    }
}
