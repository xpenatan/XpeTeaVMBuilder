package com.xpenatan.main;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Properties;

import org.teavm.tooling.RuntimeCopyOperation;
import org.teavm.tooling.TeaVMTargetType;
import org.teavm.tooling.TeaVMTool;
import org.teavm.tooling.TeaVMToolException;

public class XpeTeaVMBuilder {

	XpeTeaVMBuilderInterface builderInterface;

	public void build(XpeTeaVMBuilderInterface builderInterface) {
		this.builderInterface = builderInterface;

		URL[] urLs = ((URLClassLoader) (Thread.currentThread().getContextClassLoader())).getURLs();

		ArrayList<URL> urlsss = new ArrayList<>();

		for (int i = 0; i < urLs.length; i++) {
			URL url = urLs[i];
			String path = url.getPath();
			boolean accept = acceptPath(path);
			if(accept == false)
				continue;
			accept = builderInterface.accept(url);
			if (accept)
				urlsss.add(url);
		}
		int size = urlsss.size();

		if(size <= 0)
			return;

		System.out.println("---------------Allowed path---------------");
		for(int i = 0; i < size;i++) {
			URL url = urlsss.get(i);
			System.out.println(url.getPath());
		}
		System.out.println("------------------------------------------");

		URL[] classPaths = new URL[size];
		urlsss.toArray(classPaths);
		ClassLoader classLoader = new URLClassLoader(classPaths, XpeTeaVMBuilder.class.getClassLoader());

		TeaVMTool tool = new TeaVMTool();

		boolean setDebugInformationGenerated = false;
		boolean setSourceMapsFileGenerated = false;
		boolean setSourceFilesCopied = false;

		String targetDirectory = builderInterface.getTargetDirectory();

		System.out.println("targetDirectory: " + targetDirectory);

		File setTargetDirectory = new File(targetDirectory + "\\" + "teavm");
		String setTargetFileName = "app.js";
		boolean setMinifying = builderInterface.minifying();
		String mainClass = builderInterface.getMain();
		File setCacheDirectory = new File("C:\\TeaVMCache");;
		boolean setIncremental = false;

		tool.setClassLoader(classLoader);
		tool.setDebugInformationGenerated(setDebugInformationGenerated);
		tool.setSourceMapsFileGenerated(setSourceMapsFileGenerated);
		tool.setSourceFilesCopied(setSourceFilesCopied);
		tool.setTargetDirectory(setTargetDirectory);
		tool.setTargetFileName(setTargetFileName);
		tool.setMinifying(setMinifying);
		tool.setRuntime(mapRuntime(builderInterface.getRuntime()));
		tool.setMainClass(mainClass);
		//		tool.getProperties().putAll(profile.getProperties());
		tool.setIncremental(setIncremental);
		tool.setCacheDirectory(setCacheDirectory);
		tool.setTargetType(TeaVMTargetType.JAVASCRIPT);
		Properties properties = tool.getProperties();

		properties.put("teavm.libgdx.fsJsonPath", targetDirectory + "\\" + "filesystem.json");
		properties.put("teavm.libgdx.warAssetsDirectory", targetDirectory + "\\" + "assets");


		String assetsOutputPath = targetDirectory + "\\assets";
		ArrayList<File> paths = new ArrayList<>();
		ArrayList<String> classPathFiles = new ArrayList<>();
		assetsDefaultClasspath(classPathFiles);
		this.builderInterface.assetsPath(paths);
		AssetsCopy.copy(paths, classPathFiles, assetsOutputPath, false);


		try {
			tool.generate();
			if (!tool.wasCancelled()) {

			}

		} catch (TeaVMToolException e) {
			e.printStackTrace();
		}

		//		tool.

	}

	private boolean acceptPath(String path) {
		boolean isValid = true;

//		System.out.println("path 1: " + path);

		if(path.contains("teavm-") && path.contains(".jar"))
			isValid = false;
		if(path.contains("junit"))
			isValid = false;
		if(path.contains("hamcrest"))
			isValid = false;
		if(path.contains("jackson-"))
			isValid = false;
		if(path.contains("gdx-jnigen"))
			isValid = false;
		if(path.contains("Java/jdk"))
			isValid = false;
		if(path.contains("/XpeTeaVM"))
			isValid = false;
		if(path.contains("test-"))
			isValid = false;
		if(path.contains("commons-io"))
			isValid = false;
		if(path.contains("org/ow2"))
			isValid = false;
		if(path.contains("carrotsearch"))
			isValid = false;
		if(path.contains("google/code"))
			isValid = false;
		if(path.contains("jcraft"))
			isValid = false;
		if(path.contains("joda-time"))
			isValid = false;
		if(path.contains("mozilla"))
			isValid = false;
//		if(path.contains("gdx-"))
//			isValid = false;

//		isValid = true;

		return isValid;
	}

	private void assetsDefaultClasspath (ArrayList<String> filePath) {
		filePath.add("com/badlogic/gdx/graphics/g3d/particles/");
		filePath.add("com/badlogic/gdx/graphics/g3d/shaders/");
		filePath.add("com/badlogic/gdx/utils/arial-15.fnt"); // Cannot be utils folder for now because its trying to copy from emu folder and not core gdx classpath
		filePath.add("com/badlogic/gdx/utils/arial-15.png");
		builderInterface.assetsClasspath(filePath);
	}



	private RuntimeCopyOperation mapRuntime(XpeTeaVMRunTime runtimeMode) {
		switch (runtimeMode) {
		case MERGE:
			return RuntimeCopyOperation.MERGED;
		case SEPARATE:
			return RuntimeCopyOperation.SEPARATE;
		default:
			return RuntimeCopyOperation.NONE;
		}
	}

	public enum XpeTeaVMRunTime {
		MERGE, SEPARATE, NONE
	}
}
