package DatabaseAccess.Responses;

import DatabaseAccess.Requests.DatabaseRequest;
import LightEntities.LightSubject;

import java.util.List;

public class ViewSubjectsResponse extends DatabaseResponse {
    private List<LightSubject> lightSubjectList;

    public ViewSubjectsResponse(int status, DatabaseRequest request, List<LightSubject> lightSubjectList) {
        super(status, request);
        this.lightSubjectList = lightSubjectList;
    }

    public List<LightSubject> getLightSubjectList() {
        return lightSubjectList;
    }

    public void setLightSubjectList(List<LightSubject> lightSubjectList) {
        this.lightSubjectList = lightSubjectList;
    }
}
