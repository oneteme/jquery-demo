/**
 * 
 */
package io.github.oneteme.jquery.demo.controller;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * READ INTO THE JSON CONTAINING ALL OF THE TUTORIALS
 */
@RestController
public class FileController {

    @GetMapping("/markdown-files")
    public ResponseEntity<List<String>> getMarkdownFiles() throws IOException {
    	ClassPathResource resource = new ClassPathResource("META-INF/resources/tutorials/java");
        Path root = Paths.get(resource.getURI());
        
        try (Stream<Path> walk = Files.walk(root)) {
            List<String> mdFiles = walk
                .filter(Files::isRegularFile)
                .filter(p -> p.toString().endsWith(".md"))
                .map(p -> root.relativize(p).toString().replace("\\", "/").toLowerCase())
                .collect(toList());

            return ResponseEntity.ok(mdFiles);
        } catch (IOException e) {
            return ResponseEntity.ok(emptyList());
        }
    }
}
