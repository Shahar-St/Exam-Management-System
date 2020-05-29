package org.args.Client;

public interface IMainScreenData {

    String getName();

    void setName(String name);

    void login(String userName,String password);

    void loadSubjects();

    String getPermission();
}
