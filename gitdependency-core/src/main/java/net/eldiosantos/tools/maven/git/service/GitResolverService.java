package net.eldiosantos.tools.maven.git.service;

import net.eldiosantos.tools.maven.git.exception.GitDependencyManagerException;
import net.eldiosantos.tools.maven.git.model.RepoDescriptor;
import org.eclipse.jgit.api.Git;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;

/**
 * This service reesolves the git
 * repositories to a given folder.
 */
public class GitResolverService {

    private final static Logger LOGGER = LoggerFactory.getLogger(GitResolverService.class);

    public File resolve(RepoDescriptor repoDescriptor) {

        try {
            File destFolder = repoDescriptor.getDestFolder();

            final Git git;

            final FilenameFilter filter = (dir, name) -> name.endsWith(".git");
            final Boolean repoOrFileExists = (destFolder.exists()) &&
                    (destFolder.listFiles(filter).length > 0);

            if (!repoOrFileExists) {
                LOGGER.info("Creating dest folder '{}'...", destFolder.getAbsolutePath());
                destFolder.mkdirs();
                git = Git.cloneRepository()
                        .setURI(repoDescriptor.getUrl())
                        .setDirectory(destFolder)
                        .call();
            } else {
                git = Git.open(new File(repoDescriptor.getDestFolder().getAbsolutePath()));
            }

            git.checkout()
                    .addPath(git.getRepository().getTags().get(repoDescriptor.getVersion()).getName())
                    .call();

            return git.getRepository().getDirectory().getParentFile();
        } catch (Exception e) {
            throw new GitDependencyManagerException("Error trying to fetch git repository.", e);
        }
    }
}
