import java.io.FileInputStream;
import java.util.Properties;
import java.util.Random;

public class RandomName {
    private static String line=null;
    private static String[] lines=null;
    private static Properties properties=null;
    private static void getProperties(FileInputStream fis){
        try {
            properties = new Properties();
            properties.load(fis);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static int getRandomIndex(int len){
        return new Random().nextInt(lines.length);
    }
    public static String getRandomName(){
        try (FileInputStream fi=new FileInputStream("names.properties")){
            getProperties(fi);
            lines = properties.getProperty("name").split(",");
            line = lines[getRandomIndex(lines.length)];
            return new String(line.getBytes("ISO-8859-1"));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("oHO!发生错误了");
        }
        return "老板没有虾啊";
    }
    public static String getRandomName(String namePattern){
        try (FileInputStream fi=new FileInputStream("names.properties")){
            getProperties(fi);
            lines = properties.getProperty("namePattern").split(",");
            line = lines[getRandomIndex(lines.length)];
            return new String(line.getBytes("ISO-8859-1"));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("oHO!发生错误了");
        }
        return "老板没有虾啊";
    }
    public static String getRandomName(String namePattern,String filePath){
        try (FileInputStream fi=new FileInputStream(filePath)){
            getProperties(fi);
            lines = properties.getProperty(namePattern).split(",");
            line = lines[getRandomIndex(lines.length)];
            return new String(line.getBytes("ISO-8859-1"));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("oHO!发生错误了");
        }
        return "老板没有虾啊";
    }

    public static void main(String[] args) {
        System.out.println(RandomName.getRandomName());
    }

}
