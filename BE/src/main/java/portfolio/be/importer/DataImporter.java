package portfolio.be.importer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import portfolio.be.service.PostImportService;

@Component
public class DataImporter implements CommandLineRunner {

    @Autowired
    private PostImportService postImportService;

    @Override
    public void run(String... args) throws Exception {
//        blogImportService.importMarkdownFiles("/Users/kangdoowon/workspace/portfolio-dev/FE/_posts/");
        postImportService.importMarkdownFiles("");
    }
}