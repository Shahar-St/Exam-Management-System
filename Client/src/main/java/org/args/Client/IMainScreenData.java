package org.args.Client;

public interface IMainScreenData {

    String getName();

    void setName(String name);

    void login(String userName,String password);

    void loadSubjects();

    String getPermission();

    void setUserName(String userName);

    void studentTakeComputerizedExam(String examCode, String id);

    void studentTakeManualExam(String code);


}
