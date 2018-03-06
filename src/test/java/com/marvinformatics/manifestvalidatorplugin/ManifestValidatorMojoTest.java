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

import static org.hamcrest.CoreMatchers.containsString;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.handler.DefaultArtifactHandler;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.takari.maven.testing.TestMavenRuntime;
import io.takari.maven.testing.TestResources;

public class ManifestValidatorMojoTest {

    @Rule
    public final TestResources resources = new TestResources();

    @Rule
    public final TestMavenRuntime maven = new TestMavenRuntime();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private MavenProject project;

    private MavenSession session;

    private File basedir;

    @Before
    public void setup() throws Exception {
        basedir = resources.getBasedir("passing");
        project = maven.readMavenProject(basedir);
        session = maven.newMavenSession(project);
    }

    @Test
    public void missingFile() throws Exception {
        thrown.expect(MojoFailureException.class);
        thrown.expectMessage(containsString("File do not exist"));

        project.addAttachedArtifact(manifestedJar(new File("")));
        maven.executeMojo(session, project, "manifest-validator");
    }

    @Test
    public void notAJar() throws Exception {
        thrown.expect(MojoFailureException.class);
        thrown.expectMessage(containsString("Error reading jar"));

        project.addAttachedArtifact(manifestedJar(new File(basedir, "bummer.txt")));
        maven.executeMojo(session, project, "manifest-validator");
    }

    @Test
    public void classNotFound() throws Exception {
        thrown.expect(MojoFailureException.class);
        thrown.expectMessage(containsString("Java class 'test.Main' not found"));

        project.addAttachedArtifact(manifestedJar(new File(basedir, "no_class.zip")));
        maven.executeMojo(session, project, "manifest-validator");
    }

    @Test
    public void caseSensitive() throws Exception {
        thrown.expect(MojoFailureException.class);
        thrown.expectMessage(containsString("Java class 'test.Main' not found"));

        project.addAttachedArtifact(manifestedJar(new File(basedir, "case.zip")));
        maven.executeMojo(session, project, "manifest-validator");
    }

    @Test
    public void attachedArtifact() throws Exception {
        project.addAttachedArtifact(manifestedJar(new File(basedir, "ok.zip")));
        maven.executeMojo(session, project, "manifest-validator");
    }

    @Test
    public void projectArtifact() throws Exception {
        project.setArtifact(manifestedJar(new File(basedir, "ok.zip")));
        maven.executeMojo(session, project, "manifest-validator");
    }

    private Artifact manifestedJar(File file) {
        final Artifact artifact = new DefaultArtifact("test", "test", "1", "compile", "jar", null,
                new DefaultArtifactHandler());
        artifact.setFile(file);
        return artifact;
    }

    public static Xpp3Dom newParameter(String name, String[] values) {
        final Xpp3Dom child = new Xpp3Dom(name);
        child.setValue(Arrays.asList(values).stream()
                .collect(Collectors.joining("\n", "<entry>", "</entry>")));
        return child;
    }
}
