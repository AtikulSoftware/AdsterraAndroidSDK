package com.devatikul.adsterrasdk;

/**
 * Supported Adsterra banner ad sizes.
 * Use the sizes that match your Adsterra dashboard's banner ad units.
 */
public enum AdSize {
    BANNER_320x50(320, 50),
    LARGE_BANNER_320x100(320, 100),
    MEDIUM_RECTANGLE_300x250(300, 250),
    LEADERBOARD_728x90(728, 90),
    SKYSCRAPER_160x600(160, 600),
    HALF_PAGE_300x600(300, 600);

    private final int width;
    private final int height;

    AdSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
