import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    public static void main(String[] args) throws Exception {
        SocketConnector s = new SocketConnector();

        ExecutorService producerexecutorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            producerexecutorService.execute(s.producer);
        }

        ExecutorService consumerexecutorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            consumerexecutorService.execute(s.consumer);
        }
//        new QueryDatabase().update("222");


    }

}


