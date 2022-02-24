package e1.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

public class E7TimeOut {
    @Test
    @Timeout(5)
    void test1() throws InterruptedException {
        TimeUnit.SECONDS.sleep(6);
    }

}
