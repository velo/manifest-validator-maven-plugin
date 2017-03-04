package com.marvinformatics.manifestvalidatorplugin.oo;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;

public class Entry {

	private static final Map<String, ManifestRule> RULES = ImmutableMap.<String, ManifestRule>builder()
			.put("PRESENT", new PresentRule())
			.put("ABSENT", new AbsentRule())
			.put("VALID_CLASS", new ValidClassRule())
			.build();

	private final String attribute;

	private final Stream<ManifestRule> rules;

	public Entry(String entryString) {
		final String[] tuple = entryString.split(":");
		if (tuple.length != 2)
			throw new IllegalArgumentException(
					"Invalid entry: '" + entryString + "' expecting 'attribute:operation[,operation]' ");

		attribute = tuple[0].trim();
		rules = Arrays.asList(tuple[1].split(",")).stream()
				.map(rule -> rule.trim())
				.map(rule -> RULES.get(rule));
	}

	public Stream<Optional<String>> validate(Manifest manifest, JarFile jar) {
		return rules.map(rule -> rule.validate(manifest.getMainAttributes().getValue(attribute), jar));
	}

}
