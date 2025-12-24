public enum Sound {
    CLICK("/sounds/click.wav"),
    MOVE("/sounds/move.wav"),
    WIN("/sounds/win.wav"),
    LOSS("/sounds/loss.wav"),
    ERROR("/sounds/error.wav"),
    SELECT("/sounds/select.wav"),
    START("/sounds/Start.wav");

    public final String path;
    Sound(String path) {
        this.path = path;
    }
}
