import com.collegeproject.studygears.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class OpenAiServiceTest implements CommandLineRunner {

    private final OpenAiService openAiService;

    @Autowired
    public OpenAiServiceTest(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    @Override
    public void run(String... args) throws Exception {
        String question = "What is the capital of France?";
        String answer = openAiService.getAnswer(question);
        System.out.println("Answer: " + answer);
    }
}
