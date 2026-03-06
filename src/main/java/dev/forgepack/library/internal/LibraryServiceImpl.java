package dev.forgepack.library.internal;

import dev.forgepack.library.api.LibraryService;

public class LibraryServiceImpl implements LibraryService {
    @Override
    public void process() {
        System.out.println("Hello Library");
    }
}
