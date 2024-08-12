import com.nnk.springboot.Application;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
public class ApplicationTest {

    @Test
    public void contextLoads() {
        Application.main(new String[]{});
    }
}
