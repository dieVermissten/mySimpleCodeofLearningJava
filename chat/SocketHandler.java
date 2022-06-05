import java.io.IOException;
import java.net.Socket;

class SocketHandler {
    private SocketAttribute socketAttribute;
    public void handle(Socket socket, ServHandler servlet) {
        socketAttribute = new SocketAttribute(socket);
        servlet.getRequest(socketAttribute.request);
        socketAttribute.response = servlet.setResponse();
        socketAttribute.setOutStream();
        socketAttribute.streamclose();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
