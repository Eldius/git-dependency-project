package net.eldiosantos.tools.git.service;

import net.eldiosantos.tools.git.model.RepoDescriptor;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * This service reesolves the git
 * repositories to a given folder.
 */
public class GitResolverService {

    private final static Logger LOGGER = LoggerFactory.getLogger(GitResolverService.class);

    public File resolve(RepoDescriptor repoDescriptor) throws IOException, GitAPIException {

        File destFolder = repoDescriptor.getDestFolder();
        LOGGER.info("Creating dest folder '{}'...", destFolder.getAbsolutePath());
        if (!destFolder.exists()) {
            destFolder.mkdirs();
        }

        final Git git = Git.cloneRepository()
                .setURI(repoDescriptor.getUrl())
                .setDirectory(destFolder)
                .call();

        git.checkout()
                .addPath(repoDescriptor.getVersion())
                .call();

        return git.getRepository().getDirectory().getParentFile();
    }
}
