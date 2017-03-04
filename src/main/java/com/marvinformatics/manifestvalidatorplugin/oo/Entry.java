/**
 * Copyright (C) 2017 Marvin Herman Froeder (velobr@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.marvinformatics.manifestvalidatorplugin.oo;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;

public class Entry {

    private static final Map<String, ManifestRule> RULES = ImmutableMap.<String, ManifestRule> builder()
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
