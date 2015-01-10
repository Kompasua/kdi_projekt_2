import java.io.*;

public class FReader {

    public String readFile(String fileName) throws IOException {
        //Do not forget to change absolute path! 
        String path = "/home/kompas/Documents/Storable/workspace/Projekt_2/src/" + fileName;
        String allData = "";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                allData += sCurrentLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allData;
    }
}
