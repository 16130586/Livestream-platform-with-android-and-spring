package viewModel;

public class StreamTypeViewModel {
    private int id;
    private String typeName;
    private int numberOfType;
    private String typeThumnailImage;

    public StreamTypeViewModel() {

    }

    public int getId() {
        return id;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getNumberOfType() {
        return numberOfType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setNumberOfType(int numberOfType) {
        this.numberOfType = numberOfType;
    }

    public String getTypeThumnailImage() {
        return typeThumnailImage;
    }

    public void setTypeThumnailImage(String typeThumnailImage) {
        this.typeThumnailImage = typeThumnailImage;
    }
}

