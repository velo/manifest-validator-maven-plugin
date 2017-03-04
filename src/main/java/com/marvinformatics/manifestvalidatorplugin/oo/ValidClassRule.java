package com.marvinformatics.manifestvalidatorplugin.oo;

import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ValidClassRule implements ManifestRule {

	@Override
	public Optional<String> validate(String value, JarFile jar) {
		final JarEntry classEntry = jar.getJarEntry(value.replace(".", "/") + ".class");
		if (classEntry != null)
			return Optional.empty();
		return Optional.of("Java class '" + value + "' not found");
	}

}
