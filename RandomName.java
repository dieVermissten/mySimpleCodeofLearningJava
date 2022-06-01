import java.io.FileInputStream;
import java.util.Properties;
import java.util.Random;

public class RandomName {
    private String line = null;
    private String[] lines = null;
    private Properties properties = null;
    private String charseting = "ISO-8859-1";
    private String filePath = "names.properties";
    private String propKey = "name";
    private String seperator = ",";

    public RandomName() {

    }

    private RandomName(Builder builder) {
        charseting = builder._charseting;
        filePath = builder._filePath;
        propKey = builder._propKey;
        seperator = builder._seperator;
    }

    static class Builder {
        private String _charseting = "ISO-8859-1";
        private String _filePath = "names.properties";
        private String _propKey = "name";
        private String _seperator = ",";

        public Builder setCharseting(String charseting) {
            _charseting = charseting;
            return this;
        }

        public Builder setFilePath(String filePath) {
            _filePath = filePath;
            return this;
        }

        public Builder setpropKey(String propKey) {
            _propKey = propKey;
            return this;
        }

        public Builder setSeperator(String seperator) {
            _seperator = seperator;
            return this;
        }

        public RandomName build() {
            return new RandomName(this);
        }
    }

    private void getProperties(FileInputStream fis) {
        try {
            properties = new Properties();
            properties.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getRandomIndex(int len) {
        return new Random().nextInt(lines.length);
    }

    public String getRandomName() {
        try (FileInputStream fi = new FileInputStream(filePath)) {
            getProperties(fi);
            lines = properties.getProperty(propKey).split(seperator);
            line = lines[getRandomIndex(lines.length)];
            return new String(line.getBytes(charseting));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("oHO!发生错误了");
        }
        return "老板没有虾啊";
    }


    public static void main(String[] args) {
        RandomName r = new RandomName.Builder().setCharseting("ISO-8859-1").build();
        System.out.println(r.getRandomName());
        System.out.println(new RandomName().getRandomName());
    }

}
