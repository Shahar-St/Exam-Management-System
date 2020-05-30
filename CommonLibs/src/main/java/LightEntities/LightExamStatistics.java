package LightEntities;

import java.io.Serializable;
import java.util.List;

public class LightExamStatistics implements Serializable {
    private LightExam lightExam;
    private int averageGrade;
    private int median;
    private List<Double> gradesDistribution;
    private List<LightExecutedExam> lightExecutedExamList;

    public LightExamStatistics() {
    }

    public LightExamStatistics(LightExam lightExam, int averageGrade, int median, List<Double> gradesDistribution, List<LightExecutedExam> lightExecutedExamList) {
        this.lightExam = lightExam;
        this.averageGrade = averageGrade;
        this.median = median;
        this.gradesDistribution = gradesDistribution;
        this.lightExecutedExamList = lightExecutedExamList;
    }

    public LightExam getLightExam() {
        return lightExam;
    }

    public void setLightExam(LightExam lightExam) {
        this.lightExam = lightExam;
    }

    public int getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(int averageGrade) {
        this.averageGrade = averageGrade;
    }

    public int getMedian() {
        return median;
    }

    public void setMedian(int median) {
        this.median = median;
    }

    public List<Double> getGradesDistribution() {
        return gradesDistribution;
    }

    public void setGradesDistribution(List<Double> gradesDistribution) {
        this.gradesDistribution = gradesDistribution;
    }

    public List<LightExecutedExam> getLightExecutedExamList() {
        return lightExecutedExamList;
    }

    public void setLightExecutedExamList(List<LightExecutedExam> lightExecutedExamList) {
        this.lightExecutedExamList = lightExecutedExamList;
    }
}
