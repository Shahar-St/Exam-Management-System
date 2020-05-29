package LightEntities;

import java.util.List;

public class LightSubject {
    private String id;
    private String name;
    private List<LightCourse> lightCourseList;

    public LightSubject() {
    }

    public LightSubject(String id, String name, List<LightCourse> lightCourseList) {
        this.id = id;
        this.name = name;
        this.lightCourseList = lightCourseList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LightCourse> getLightCourseList() {
        return lightCourseList;
    }

    public void setLightCourseList(List<LightCourse> lightCourseList) {
        this.lightCourseList = lightCourseList;
    }
}
