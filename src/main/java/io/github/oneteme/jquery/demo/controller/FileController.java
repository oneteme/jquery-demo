/**
 * 
 */
package io.github.oneteme.jquery.demo.controller;

import static java.nio.file.Files.walk;
import static java.nio.file.Paths.get;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

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
    public ResponseEntity<List<String>> getMarkdownFiles()  {
    	var resource = new ClassPathResource("META-INF/resources/tutorials/java");
        try {
        	var root = get(resource.getURI());
            try (var walk = walk(get(resource.getURI()))) {
                var mdFiles = walk
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".md"))
                    .map(p -> root.relativize(p).toString().replace("\\", "/").toLowerCase())
                    .toList();
                return ok(mdFiles);
            }
	    } catch (IOException e) {
	        return ok(emptyList());
	    }
	}
}
