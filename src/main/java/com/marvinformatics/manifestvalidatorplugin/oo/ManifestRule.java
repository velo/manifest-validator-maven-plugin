package com.marvinformatics.manifestvalidatorplugin.oo;

import java.util.Optional;
import java.util.jar.JarFile;

public interface ManifestRule {

	Optional<String> validate(String value, JarFile jar);

}
