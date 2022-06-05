import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SocketConnector {
    private static ServerSocket serverSocket = null;
    private static ArrayList<Socket> sockets = null;
    private Socket accept = null;
    private static Lock lock;
    private static Condition condition;

    public SocketConnector() {
        try {
            serverSocket = new ServerSocket(8888);
            sockets = new ArrayList<>();
            lock = new ReentrantLock();
            condition = lock.newCondition();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Runnable producer = () -> {
        while (true) {
            lock.lock();
            try {
                while (sockets.isEmpty()) {
                    accept = serverSocket.accept();
                    System.out.println(Thread.currentThread().getName() + " new Connection");
                    sockets.add(accept);
                    condition.signalAll();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    };
    public Runnable consumer = () -> {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            try {
                while (sockets.isEmpty()) {
                    condition.await();
                }
                System.out.print(Thread.currentThread().getName() + " ");
                excute(sockets.remove(0));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }


    };

    private void excute(Socket accept) {
        new SocketHandler().handle(accept, new ServHandler() {
            private RouteEntry routeEntry = null;

            @Override
            public void getRequest(String re) {
                routeEntry = new Route(re).getRouteEntry();
                System.out.println(routeEntry.toString());
            }

            @Override
            public byte[] setResponse() {
                ByteArrayOutputStream bos = null;
                try {
                    String responseHeader = "HTTP/1.1 200 ok\r\n\r\n";
                    bos = new ByteArrayOutputStream();
                    bos.writeBytes(responseHeader.getBytes());
                    //src/resourse/45.html
                    if (routeEntry.mainRoute.contains("QueryDatabase")) {
                        String[] s = routeEntry.mainRoute.split("/");
                        Class c = Class.forName(s[1]);
                        Object object = c.getDeclaredConstructor().newInstance();
                        System.out.println(s[2]);

                        if (routeEntry.paraMap!=null){
                            Method method = c.getDeclaredMethod(s[2],HashMap.class);
                            boolean success = (Boolean) method.invoke(object,routeEntry.paraMap);
                            bos.writeBytes("添加成功 Insert successful".getBytes());
                        }else {
                            Method method = c.getDeclaredMethod(s[2]);
                            String r = (String) method.invoke(object);
                            bos.writeBytes(r.getBytes());
                        }


                    } else {
                        bos.writeBytes(Files.readAllBytes(Paths.get("src/resourse" + routeEntry.mainRoute)));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (null != bos) return bos.toByteArray();
                return null;
            }
        });
    }

}

