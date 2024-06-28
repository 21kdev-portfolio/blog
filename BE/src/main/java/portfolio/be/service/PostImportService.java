package portfolio.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import portfolio.be.entity.Post;
import portfolio.be.repository.PostRepository;

import java.io.IOException;
import org.yaml.snakeyaml.Yaml;

import org.commonmark.renderer.html.HtmlRenderer;

import java.io.File;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

@Service
public class PostImportService {

    private final PostRepository postRepository;

    @Autowired
    public PostImportService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void importMarkdownFiles(String directoryPath) throws IOException, ParseException {
        File folder = new File(directoryPath);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    String content = new String(Files.readAllBytes(file.toPath()));
                    Post post = parseMarkdownFile(content);
                    postRepository.save(post);
                }
            }
        }
    }

    private Post parseMarkdownFile(String content) throws IOException, ParseException {
        String[] parts = content.split("---", 3);
        Yaml yaml = new Yaml();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Map<String, Object> metaData = yaml.load(parts[1]);

        Post post = new Post();
        post.setTitle(metaData.get("title").toString());
        post.setTagline(metaData.get("tagline").toString());
        post.setPreview(metaData.get("preview").toString());
        post.setImage(metaData.get("image").toString());
        post.setDate(df.parse(metaData.get("date").toString()));
        post.setContent(parts[2]);

        return post;
    }
}