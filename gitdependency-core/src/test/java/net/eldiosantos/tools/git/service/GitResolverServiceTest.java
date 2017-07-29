package net.eldiosantos.tools.git.service;

import com.google.common.truth.Truth;
import net.eldiosantos.tools.git.model.RepoDescriptor;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.*;

public class GitResolverServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitResolverServiceTest.class);

    @Test
    public void resolve() throws Exception {
        final String url = "https://github.com/Eldius/light-jndi.git";
        final String name = "my-test-repo";
        final String version = "light-jndi-project-0.0.3";
        final String dest = "./target/repos";

        LOGGER.info("Destination folder: {}", new File(dest).getAbsolutePath());

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
    }

}