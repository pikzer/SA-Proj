package CheckingApp;


import java.awt.* ;
import java.io.File;
import java.io.IOException;

public class OpenFile {
    protected String fileDirectoryName;
    protected String fileName;

    public OpenFile(String fileDirectoryName, String fileName) {
        this.fileDirectoryName = fileDirectoryName;
        this.fileName = fileName;
//        checkFileIsExisted();
    }

    protected void checkFileIsExisted() {
        File file = new File(fileDirectoryName);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = fileDirectoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Cannot create " + filePath);
            }
        }
    }

    public File GetFile() {
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        return file;
    }

//    public void openFile() {
//        String filePath = fileDirectoryName + File.separator + fileName;
//        try {
//            File file = new File(filePath);
//            Desktop.getDesktop().open(file);
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        }
//    }
//}
//
}