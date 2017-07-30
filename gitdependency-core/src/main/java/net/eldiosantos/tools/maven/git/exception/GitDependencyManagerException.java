package net.eldiosantos.tools.maven.git.exception;

public class GitDependencyManagerException extends RuntimeException {
    public GitDependencyManagerException() {
    }

    public GitDependencyManagerException(String message) {
        super(message);
    }

    public GitDependencyManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public GitDependencyManagerException(Throwable cause) {
        super(cause);
    }

    public GitDependencyManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
