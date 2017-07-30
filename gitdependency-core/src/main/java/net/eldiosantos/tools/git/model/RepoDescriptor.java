package net.eldiosantos.tools.git.model;

import java.io.File;
import java.io.IOException;

public class RepoDescriptor {
    private final String url;
    private final String dest;
    private final String version;
    private final String name;

    public RepoDescriptor(String url, String dest, String version, String name) {
        this.url = url;
        this.dest = dest;
        this.version = version;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getDest() {
        return dest;
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "RepoDescriptor{" +
                "url='" + url + '\'' +
                ", dest='" + dest + '\'' +
                ", version='" + version + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public File getDestFolder() throws IOException {
        return new File(String.format("%s/%s",getDest(), getName()));
    }
}
