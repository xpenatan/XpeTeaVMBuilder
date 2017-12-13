package com.xpenatan.main;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import com.xpenatan.main.XpeTeaVMBuilder.XpeTeaVMRunTime;

public interface XpeTeaVMBuilderInterface {

	public boolean accept(URL url);

	public XpeTeaVMRunTime getRuntime();

	public String getMain();

	public String getTargetDirectory();

	public void assetsClasspath (ArrayList<String> classPaths);

	public abstract void assetsPath (ArrayList<File> paths);

	public abstract boolean minifying ();


}
