package com.marvinformatics.manifestvalidatorplugin.oo;

import java.util.Optional;
import java.util.jar.JarFile;

import com.google.common.base.Strings;

public class PresentRule implements ManifestRule {

	@Override
	public Optional<String> validate(String value, JarFile jar) {
		if (Strings.isNullOrEmpty(value))
			return Optional.of("Expected to be present");
		return Optional.empty();
	}

}
