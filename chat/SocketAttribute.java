import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class SocketAttribute {
    private InputStream inputStream;
    private OutputStream outputStream;
    public String request;
    public byte[] response;

    SocketAttribute(Socket socket) {
        try {
            inputStream = socket.getInputStream();
            request = getInString();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void streamclose() {
        try {
            if (null != inputStream) inputStream.close();
            if (null != outputStream) outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getInString() {
        byte[] b = new byte[1024 * 1024];
        int len = 0;
        try {
            len = inputStream.read(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(b, 0, len);
    }

    public void setOutStream() {
        try {
            outputStream.write(response);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
