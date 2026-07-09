package github.snomfish.graphics;

public record AssetKey(
    String name,
    Class<? extends Asset> type
) {}
