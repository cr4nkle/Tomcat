package program.model.locale;

public class Buttons {
    private String newModel;
    private String openDevice;
    private String openDataStorage;

    public Buttons() {
    }

    public Buttons(
            String newModel,
            String openDevice,
            String openDataStorage
    ) {
        this.newModel = newModel;
        this.openDevice = openDevice;
        this.openDataStorage = openDataStorage;
    }

    public String getNewModel() {
        return newModel;
    }

    public String getOpenDevice() {
        return openDevice;
    }

    public String getOpenDataStorage() {
        return openDataStorage;
    }

    public void setNewModel(String newModel) {
        this.newModel = newModel;
    }

    public void setOpenDevice(String openDevice) {
        this.openDevice = openDevice;
    }

    public void setOpenDataStorage(String openDataStorage) {
        this.openDataStorage = openDataStorage;
    }
}
