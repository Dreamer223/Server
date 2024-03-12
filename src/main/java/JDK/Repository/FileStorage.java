package JDK.Repository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class FileStorage implements Repository {

    @Override
    public void saveInLog(String text) throws IOException {
        try (FileWriter fileWriter = new FileWriter("log.txt",true)){
            fileWriter.append(text);
            fileWriter.append("\n");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public String loadLog() {
        try (FileReader fileReader = new FileReader("log.txt")) {
            StringBuilder stringBuilder = new StringBuilder();
            int c;
            while ((c = fileReader.read()) != -1) {
                stringBuilder.append((char) c);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            return "";
        }
    }
}


