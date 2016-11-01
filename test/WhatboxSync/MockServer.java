import org.apache.commons.net.ftp.FTPFile;
import org.springframework.scheduling.annotation.Async;

import java.io.*;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Future;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

/**
 * Created by JP on 10/31/2016.
 */
public class MockServer implements IServer {
    public MockServer() {

    }

    public String getAddress() {
        return "address";
    }

    public String getUsername() {
        return "username";
    }

    public String getPassword() {
        return "password";
    }

    public Integer getPort() {
        return 1;
    }

    public void connect() throws Exception {
        return;
    }

    public void disconnect() throws Exception {
        return;
    }

    public Boolean isConnected() {
        return true;
    }

    public List<FTPFile> list(String directory) throws Exception {
        return new ArrayList<FTPFile>();
    }

    public Future<Boolean> download(String sourceFile, String destinationFile) throws Exception {
        return new AsyncResult<Boolean>(true);
    }

    public Future<Boolean> download(String sourceFile, java.io.File destinationFile) throws Exception {
        return new AsyncResult<Boolean>(true);
    }

    public Future<Boolean> upload(String sourceFile, String destinationFile) throws Exception {
        return new AsyncResult<Boolean>(true);
    }

    public Future<Boolean> upload(File sourceFile, String destinationFile) throws Exception {
        return new AsyncResult<Boolean>(true);
    }
}
