package com.xpenatan.teavm.simpletest;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import com.xpenatan.main.XpeTeaVMBuilder;
import com.xpenatan.main.XpeTeaVMBuilderInterface;
import com.xpenatan.main.XpeTeaVMBuilder.XpeTeaVMRunTime;

public class Test {

	public static void main(String[] args) {

		XpeTeaVMBuilder builder = new XpeTeaVMBuilder();


		XpeTeaVMBuilderInterface liste = new XpeTeaVMBuilderInterface() {

			@Override
			public XpeTeaVMRunTime getRuntime() {
				return XpeTeaVMRunTime.SEPARATE;
			}

			@Override
			public boolean accept(URL url) {

				String path = url.getPath();

				if(path.contains("XpeTest"))
					return false;
				return true;
			}

			@Override
			public String getMain() {
				return "com.xpenatan.teavm.simpletest.Launcher";
//				return "org.teavm.libgdx.invaders.TeaVMLauncher";
			}

			@Override
			public String getTargetDirectory() {
//				return "D:\\Dev\\Projects\\teaVM_GDX\\demos\\invaders\\webapp\\webapp";
				return new File("webapp").getAbsolutePath();
			}

			@Override
			public void assetsClasspath(ArrayList<String> classPaths) {
			}

			@Override
			public void assetsPath(ArrayList<File> paths) {
//				paths.add(new File("D:\\Dev\\Projects\\teaVM_GDX\\demos\\invaders\\webapp\\src\\main\\webapp\\assets"));
			}
		};

		builder.build(liste);

	}
}
