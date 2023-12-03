package com.gen.entity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class ActionCache {

	private static final String LINE_IMPORT_CACHE = "import org.hibernate.annotations.Cache;";
	private static final String LINE_IMPORT_CACHE_STRATEGY = "import org.hibernate.annotations.CacheConcurrencyStrategy;";
	private static final String SECTION_CACHE = "@Cache(region=\"common\", usage = CacheConcurrencyStrategy.READ_WRITE)";
	private static final String SECTION_ENTITY = "@Entity";
	private static final String LINE_IMPORT_TABLE = "import javax.persistence.Table;";
	private static final String SPACE="\n";

	public static void main(String[] args) throws IOException {
		String directory = args[0];
		Collection<File> files = FileUtils.listFiles(new File(directory), null, false);
		for (File file : files) {
			List<String> lines = FileUtils.readLines(file);
			lines = filterOut(lines);
			FileUtils.writeLines(file, lines);
		}
	}

	public static List<String> filterOut(List<String> lines) {
		List<String> result = new ArrayList<String>();
		for (String line : lines) {
			String newline=null;
			if (line.equals(LINE_IMPORT_TABLE)) {
				
				line = line+SPACE+LINE_IMPORT_CACHE+SPACE+LINE_IMPORT_CACHE_STRATEGY;
			}
			if (line.contains(SECTION_ENTITY)) {
				line = line+SPACE+SECTION_CACHE;
			}
			result.add(line);

		}
		return result;
	}
}
