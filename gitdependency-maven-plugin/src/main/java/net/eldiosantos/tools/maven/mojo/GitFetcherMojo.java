package net.eldiosantos.tools.maven.mojo;

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

import net.eldiosantos.tools.maven.git.model.RepoDescriptor;
import net.eldiosantos.tools.maven.git.service.GitResolverService;
import net.eldiosantos.tools.maven.model.MavenRepoDescriptor;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;

/**
 * Refreshes the repository from the Git repository.
 */
@Mojo(
    name = "refresh"
    , defaultPhase = LifecyclePhase.GENERATE_SOURCES
)
public class GitFetcherMojo extends AbstractMojo {

    @Parameter(name = "repos")
    private List<MavenRepoDescriptor>repos;



    @Parameter( defaultValue = "${project}", readonly = true )
    private MavenProject project;


    private Stream<RepoDescriptor> getRepos() {
        return repos.stream().map(MavenRepoDescriptor::toDescriptor);
    }

    public void execute() throws MojoExecutionException {
        Log log = getLog();
        try {

            GitResolverService service = new GitResolverService();
            getRepos()
                .map(service::resolve)
                .map(File::getAbsolutePath)
                .peek(log::debug)
                .forEach(f -> project.addCompileSourceRoot(f));
        } catch (Exception e) {
            log.error("Error trying to refresh sources from git.", e);
            throw new MojoExecutionException("Error trying to refresh sources from git.", e);
        }
    }

}
