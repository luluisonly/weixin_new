package com.bokesoft.thirdparty.weixin.common;

import java.io.File;
import java.io.IOException;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

import com.bokesoft.myerp.common.StringUtil;

public class ZipUtil {
	public synchronized static boolean packageZip(String folder) throws IOException {
		if (StringUtil.isBlankOrNull(folder)) {
			return false;
		}
		String zipName = folder + ".zip";
		File zipFile = new File(zipName);
		if (zipFile.exists()) {
			return true;
		}
		File file = new File(folder);
		if (!file.exists()) {
			throw new IOException(file.getAbsolutePath() + " not found.");
		}
		Project prj = new Project();
		Zip zip = new Zip();
		zip.setProject(prj);
		zip.setDestFile(zipFile);
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		fileSet.setDir(file);
		// fileSet.setIncludes("**/*.java"); 包括哪些文件或文件夹
		// eg:zip.setIncludes("*.java");
		// fileSet.setExcludes(...); 排除哪些文件或文件夹
		zip.addFileset(fileSet);
		zip.execute();
		return true;
	}
}
