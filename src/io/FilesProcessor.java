package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FilesProcessor {

	public FilesProcessor() {
		// TODO Auto-generated constructor stub
	}
	
	public Set<String> processDictionaryFile(String path) throws IOException {
		BufferedReader r = null;
		Set<String> result = new HashSet<>();
        try {
            r = new BufferedReader(new FileReader(path));
            String line;
            while((line = r.readLine()) != null) {
                result.add(line);
            }
        }
        finally {
            if(r != null) r.close();
        }
        return result;
	}
}
