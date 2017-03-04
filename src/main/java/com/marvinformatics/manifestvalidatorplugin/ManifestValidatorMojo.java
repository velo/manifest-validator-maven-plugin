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
package com.marvinformatics.manifestvalidatorplugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import com.google.common.base.Strings;
import com.marvinformatics.manifestvalidatorplugin.oo.Entry;
import com.marvinformatics.manifestvalidatorplugin.oo.Result;

@Mojo(name = "manifest-validator", defaultPhase = LifecyclePhase.VERIFY)
public class ManifestValidatorMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    @Parameter
    private String[] includes;

    @Parameter
    private String[] entries;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final Stream<Artifact> artifacts = project.getAttachedArtifacts().stream()
                .filter(artifact -> "jar".equals(artifact.getType()))
                .filter(artifact -> included(artifact.getArtifactId()));

        final String message = artifacts
                .map(artifact -> evaluate(artifact))
                .filter(result -> result.hasWarnings())
                .map(result -> result.message())
                .collect(Collectors.joining("\n"));
        if (!Strings.isNullOrEmpty(message)) {
            getLog().error(message);
            throw new MojoFailureException(message);
        }
    }

    private Result evaluate(Artifact artifact) {
        final File file = artifact.getFile();

        if (!file.exists())
            return new Result(artifact, Arrays.asList("File do not exist").stream());

        try (final JarFile jar = new JarFile(file);) {
            final Manifest manifest = jar.getManifest();

            return new Result(artifact, Arrays.asList(entries).stream()
                    .map(entry -> new Entry(entry))
                    .map(entry -> entry.validate(manifest, jar))
                    .flatMap(errors -> errors.filter(error -> error.isPresent())
                            .map(error -> error.get())));
        } catch (final IOException e) {
            getLog().error("Error reading jar file: " + artifact, e);
            return new Result(artifact, Arrays.asList("Error reading jar file: " + artifact + e.getMessage()).stream());
        }
    }

    private boolean included(String artifactId) {
        if (includes == null || includes.length == 0)
            return true;

        for (final String include : includes)
            if (artifactId.contains(include))
                return true;

        return false;
    }

}
