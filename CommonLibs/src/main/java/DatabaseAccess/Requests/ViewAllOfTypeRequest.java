package DatabaseAccess.Requests;

public class ViewAllOfTypeRequest<T> extends DatabaseRequest {
    private Class<T> objectType;

    public ViewAllOfTypeRequest(Class<T> objectType) {
        this.objectType = objectType;
    }

    public Class<T> getObjectType() {
        return objectType;
    }

    public void setObjectType(Class<T> objectType) {
        this.objectType = objectType;
    }
}
