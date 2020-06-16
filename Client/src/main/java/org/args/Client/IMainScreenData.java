package org.args.Client;

@SuppressWarnings("ALL")
public interface IMainScreenData {

    String getName();

    void login(String userName,String password);

    void loadSubjects();

    String getPermission();

    void setUserName(String userName);

    void studentTakeComputerizedExam(String examCode, String id);

    void studentTakeManualExam(String code);

    void clearSubjectsAndCourses();
}
