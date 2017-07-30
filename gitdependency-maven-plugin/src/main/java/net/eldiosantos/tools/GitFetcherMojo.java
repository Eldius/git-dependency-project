package net.eldiosantos.tools;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import net.eldiosantos.tools.git.model.RepoDescriptor;
import net.eldiosantos.tools.git.service.GitResolverService;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Refreshes the repository from the Git repository.
 */
@Mojo(
    name = "refresh"
    , defaultPhase = LifecyclePhase.GENERATE_SOURCES
)
public class GitFetcherMojo extends AbstractMojo {

    /**
     * The Git repository configuration.
     * url: the Git URL
     */
    @Parameter(property = "gitdependency.repo.url", required = true)
    private String url;

    /**
     * The Git repository configuration.
     * name: the folder name to be used in the src/generated
     */
    @Parameter(property = "gitdependency.repo.name", required = true)
    private String name;

    /**
     * The Git repository configuration.
     * dest: the destination folder (should be src/generated)
     */
    @Parameter(property = "gitdependency.repo.dest", defaultValue = "src/main/generated")
    private String dest;

    /**
     * The Git repository configuration.
     * version: the Git tag to be used
     */
    @Parameter(property = "gitdependency.repo.version", required = true)
    private String version;

    private RepoDescriptor getRepo() {
        return new RepoDescriptor(url, dest, version, name);
    }

    public void execute() throws MojoExecutionException {
        try {
            new GitResolverService().resolve(getRepo());
        } catch (Exception e) {
            getLog().error("Error trying to refresh sources from git.", e);
            throw new MojoExecutionException("Error trying to refresh sources from git.", e);
        }
    }
}
