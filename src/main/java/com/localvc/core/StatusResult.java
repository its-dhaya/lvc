package com.localvc.core;

import java.nio.file.Path;
import java.util.Set;

public class StatusResult {

    public final Set<Path> unchanged;
    public final Set<Path> modified;
    public final Set<Path> added;
    public final Set<Path> deleted;

    public StatusResult(
            Set<Path> unchanged,
            Set<Path> modified,
            Set<Path> added,
            Set<Path> deleted
    ) {
        this.unchanged = unchanged;
        this.modified = modified;
        this.added = added;
        this.deleted = deleted;
    }
}
