package com.okta.example.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import javax.inject.Inject;

/**
 * An example Maven Mojo that resolves the current project's git revision and adds that a new {@code exampleVersion}
 * property to the current Maven project.
 */
@Mojo(name = "version", defaultPhase = LifecyclePhase.INITIALIZE)
public class GitVersionMojo extends AbstractMojo {

    /**
     * The git command used to retrieve the current commit hash.
     */
    @Parameter(property = "git.command", defaultValue = "git rev-parse --short HEAD")
    private String command;

    @Parameter(property = "project", readonly = true)
    private MavenProject project;

    @Inject
    private VersionProvider versionProvider;

    public void execute() throws MojoExecutionException, MojoFailureException {
        String version = versionProvider.getVersion(command);
        project.getProperties().put("exampleVersion", version);
        getLog().info("Git hash: " + version);
    }
}
