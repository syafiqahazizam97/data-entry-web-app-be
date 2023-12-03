package com.gen.entity;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.persistence.Table;

import org.apache.commons.io.FileUtils;

public class ActionRemoveComments {
	public static void main(String[] args) throws IOException {
		String directory = args[0];
		Collection<File> files = FileUtils.listFiles(new File(directory), null, false);
		for (File file : files) {
			List<String> lines = FileUtils.readLines(file);
			if (lines.size() > 2 && lines.get(1).startsWith("// Generated")) {
				lines.remove(1);
			}
			
			for(int i=0;i<lines.size();i++){
				String line = lines.get(i);
				if (line.startsWith("wdmsdb")) {
					line.replace("wdmsdb", "*");
					lines.set(i, line);
					break;
				}
			}
			/*int start = 0;
			int end = 0;
			for(int i=0;i<lines.size();i++){
				String line = lines.get(i);
				if (line.startsWith("@Table")) {
					start = i;
				}
				if(line.startsWith("public class")){
					end = i;
					break;
				}
			}
			
			if(end>0){
				for(int i=end-1;i>=start;i--){
					lines.remove(i);
				}
			}*/
			
			
			FileUtils.writeLines(file, lines);
		}
	}
}
