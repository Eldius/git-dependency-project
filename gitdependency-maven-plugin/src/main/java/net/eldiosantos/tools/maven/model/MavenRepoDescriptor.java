package net.eldiosantos.tools.maven.model;

import net.eldiosantos.tools.maven.git.model.RepoDescriptor;
import org.apache.maven.plugins.annotations.Parameter;

public class MavenRepoDescriptor {
    /**
     * The Git repository configuration.
     * url: the Git URL
     */
    @Parameter(property = "url", required = true)
    private String url;

    /**
     * The Git repository configuration.
     * name: the folder name to be used in the src/generated
     */
    @Parameter(property = "name", required = true)
    private String name;

    /**
     * The Git repository configuration.
     * dest: the destination folder (should be src/generated)
     */
    @Parameter(property = "dest", defaultValue = "src/main/generated")
    private String dest;

    /**
     * The Git repository configuration.
     * version: the Git tag to be used
     */
    @Parameter(property = "version", required = true)
    private String version;

    public MavenRepoDescriptor() {
    }

    public MavenRepoDescriptor(String url, String dest, String version, String name) {
        this.url = url;
        this.dest = dest;
        this.version = version;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public MavenRepoDescriptor setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getDest() {
        return dest;
    }

    public MavenRepoDescriptor setDest(String dest) {
        this.dest = dest;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public MavenRepoDescriptor setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getName() {
        return name;
    }

    public MavenRepoDescriptor setName(String name) {
        this.name = name;
        return this;
    }

    public RepoDescriptor toDescriptor() {
        return new RepoDescriptor(getUrl(), getDest(), getVersion(), getName());
    }

    @Override
    public String toString() {
        return "MavenRepoDescriptor{" +
                "url='" + url + '\'' +
                ", dest='" + dest + '\'' +
                ", version='" + version + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
