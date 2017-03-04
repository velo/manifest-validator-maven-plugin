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

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.maven.artifact.Artifact;

public class Result {

    private final Artifact artifact;
    private final String warnings;

    public Result(Artifact artifact, Stream<String> warnings) {
        this.artifact = artifact;
        this.warnings = warnings.collect(Collectors.joining("\n", " * ", ""));
    }

    public boolean hasWarnings() {
        return warnings.length() > 5;
    }

    public String message() {
        return "Artifact: '" + artifact + "' failed the following validations: \n" + warnings;
    }

}
