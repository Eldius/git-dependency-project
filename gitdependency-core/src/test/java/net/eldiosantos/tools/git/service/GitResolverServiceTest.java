package net.eldiosantos.tools.git.service;

import com.google.common.truth.Truth;
import net.eldiosantos.tools.git.model.RepoDescriptor;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Ref;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class GitResolverServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitResolverServiceTest.class);

    @Test
    public void resolve() throws Exception {
        final String url = "https://github.com/Eldius/light-jndi.git";
        final String name = "my-test-repo";
        final String version = "light-jndi-project-0.0.2";
        final String dest = "./target/repos";

        final File file = new File(dest);
        LOGGER.info("Destination folder: {}", file.getAbsolutePath());
        LOGGER.info("Does the file exists: {}", file.exists());

        final RepoDescriptor descriptor = new RepoDescriptor(url, dest, version, name);
        final File destFolder = new GitResolverService().resolve(descriptor);

        LOGGER.info("destFolder = {}", destFolder.getAbsolutePath());

        Truth.assertWithMessage("It's in the right folder")
                .that(descriptor.getDestFolder())
                .isEqualTo(destFolder);

        Truth.assertWithMessage("The right folder exists")
                .that(descriptor.getDestFolder().exists())
                .isTrue();

        Truth.assertWithMessage("It has a pom.xml file")
                .that(Arrays.asList(
                        descriptor
                                .getDestFolder()
                                .listFiles())
                        .parallelStream()
                        .filter(f -> f.getName().endsWith("pom.xml")).findAny().isPresent()
                ).isTrue();

        Git git = Git.open(new File(String.format("%s/.git", destFolder.getAbsolutePath())));
        final String currentTag = git.getRepository().getTags().get(descriptor.getVersion()).toString();

        final String rightVersion = git.getRepository().getTags().get(version).toString();

        LOGGER.info("\nRight TAG:\t{}\nCurrent TAG:\t{}", rightVersion, currentTag);

        Truth.assertWithMessage("Iy's on the right version")
                .that(currentTag)
                .isEqualTo(rightVersion);

        destFolder.deleteOnExit();
    }

    @Test
    public void resolveWithExistingFolder() throws Exception {
        final String url = "https://github.com/Eldius/light-jndi.git";
        final String name = "my-test-repo";
        final String version = "light-jndi-project-0.0.2";
        final String dest = "./target/repos2";

        final File file = new File(dest);
        File projectFolder = new File(String.format("%s/%s/", file.getAbsolutePath(), name));
        projectFolder.mkdirs();
        LOGGER.info("Destination folder: {}", file.getAbsolutePath());
        LOGGER.info("Does the file exists: {}", file.exists());

        final RepoDescriptor descriptor = new RepoDescriptor(url, dest, version, name);
        final File destFolder = new GitResolverService().resolve(descriptor);

        LOGGER.info("destFolder = {}", destFolder.getAbsolutePath());

        Truth.assertWithMessage("It's in the right folder")
                .that(descriptor.getDestFolder())
                .isEqualTo(destFolder);

        Truth.assertWithMessage("The right folder exists")
                .that(descriptor.getDestFolder().exists())
                .isTrue();

        Truth.assertWithMessage("It has a pom.xml file")
                .that(Arrays.asList(
                        descriptor
                                .getDestFolder()
                                .listFiles())
                        .parallelStream()
                        .filter(f -> f.getName().endsWith("pom.xml")).findAny().isPresent()
                ).isTrue();

        Git git = Git.open(new File(String.format("%s/.git", destFolder.getAbsolutePath())));
        final String currentTag = git.getRepository().getTags().get(descriptor.getVersion()).toString();

        final String rightVersion = git.getRepository().getTags().get(version).toString();

        LOGGER.info("\nRight TAG:\t{}\nCurrent TAG:\t{}", rightVersion, currentTag);

        Truth.assertWithMessage("Iy's on the right version")
                .that(currentTag)
                .isEqualTo(rightVersion);

        destFolder.deleteOnExit();
    }

    @Test
    public void resolveWithExistingRepo() throws Exception {
        final String url = "https://github.com/Eldius/light-jndi.git";
        final String name = "my-test-repo";
        final String version = "light-jndi-project-0.0.2";
        final String dest = "target/repos3";

        final File file = new File(dest);
        final File projectFolder = new File(String.format("%s/%s/", file.getAbsolutePath(), name));
        projectFolder.mkdirs();
        LOGGER.info("Destination folder: {}", file.getAbsolutePath());
        LOGGER.info("Does the file exists: {}", file.exists());

        final RepoDescriptor descriptor = new RepoDescriptor(url, dest, version, name);
        final File destFolder = new GitResolverService().resolve(descriptor);
        final File destFolder2 = new GitResolverService().resolve(descriptor);

        LOGGER.info("[resolveWithExistingRepo] destFolder\t= {}", destFolder.getAbsoluteFile().getAbsolutePath());
        LOGGER.info("[resolveWithExistingRepo] destFolder2\t= {}", destFolder2.getAbsoluteFile().getAbsolutePath());

        Truth.assertWithMessage("The two folders are equals")
                .that(destFolder2.getAbsoluteFile())
                .isEqualTo(destFolder.getAbsoluteFile());

        Truth.assertWithMessage("It's in the right folder")
                .that(descriptor.getDestFolder())
                .isEqualTo(destFolder);

        Truth.assertWithMessage("The right folder exists")
                .that(descriptor.getDestFolder().exists())
                .isTrue();

        Truth.assertWithMessage("It has a pom.xml file")
                .that(Arrays.asList(
                        descriptor
                                .getDestFolder()
                                .listFiles())
                        .parallelStream()
                        .filter(f -> f.getName().endsWith("pom.xml")).findAny().isPresent()
                ).isTrue();

        Git git = Git.open(new File(String.format("%s/.git", destFolder.getAbsolutePath())));
        final String currentTag = git.getRepository().getTags().get(descriptor.getVersion()).toString();

        final String rightVersion = git.getRepository().getTags().get(version).toString();

        LOGGER.info("\nRight TAG:\t{}\nCurrent TAG:\t{}", rightVersion, currentTag);

        Truth.assertWithMessage("Iy's on the right version")
                .that(currentTag)
                .isEqualTo(rightVersion);

        destFolder.deleteOnExit();
    }

    @Test(expected = JGitInternalException.class)
    public void resolveWithExistingInvalidRepo() throws Exception {
        final String url = "https://github.com/Eldius/light-jndi.git";
        final String name = "my-test-repo";
        final String version = "light-jndi-project-0.0.2";
        final String dest = "./target/repos4";

        final File file = new File(dest);
        File projectFolder = new File(String.format("%s/%s/my/custom/invalid/folder", file.getAbsolutePath(), name));
        projectFolder.mkdirs();
        LOGGER.info("Destination folder: {}", file.getAbsolutePath());
        LOGGER.info("Does the file exists: {}", file.exists());

        final RepoDescriptor descriptor = new RepoDescriptor(url, dest, version, name);
        final File destFolder = new GitResolverService().resolve(descriptor);

        LOGGER.info("destFolder = {}", destFolder.getAbsolutePath());

        Truth.assertWithMessage("It's in the right folder")
                .that(descriptor.getDestFolder())
                .isEqualTo(destFolder);

        Truth.assertWithMessage("The right folder exists")
                .that(descriptor.getDestFolder().exists())
                .isTrue();

        Truth.assertWithMessage("It has a pom.xml file")
                .that(Arrays.asList(
                        descriptor
                                .getDestFolder()
                                .listFiles())
                        .parallelStream()
                        .filter(f -> f.getName().endsWith("pom.xml")).findAny().isPresent()
                ).isTrue();

        Git git = Git.open(new File(String.format("%s/.git", destFolder.getAbsolutePath())));
        final String currentTag = git.getRepository().getTags().get(descriptor.getVersion()).toString();

        final String rightVersion = git.getRepository().getTags().get(version).toString();

        LOGGER.info("\nRight TAG:\t{}\nCurrent TAG:\t{}", rightVersion, currentTag);

        Truth.assertWithMessage("Iy's on the right version")
                .that(currentTag)
                .isEqualTo(rightVersion);

        destFolder.deleteOnExit();
    }

}
