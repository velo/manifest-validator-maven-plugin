package com.marvinformatics.manifestvalidatorplugin.oo;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.maven.artifact.Artifact;

public class Result  {

	private final Artifact artifact;
	private final String warnings;

	public Result(Artifact artifact, Stream<String> warnings) {
		this.artifact = artifact;
		this.warnings = warnings.collect(Collectors.joining("\n", " * ", ""));
	}

	public boolean hasWarnings() {
		return warnings.length() != 0;
	}

	public String message() {
		return "Artifact: '" + artifact + "' failed the following validations: \n" + warnings;
	}

}
