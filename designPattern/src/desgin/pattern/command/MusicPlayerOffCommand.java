package desgin.pattern.command;

public class MusicPlayerOffCommand implements Command {

    private MusicPlayer musicPlayer;

    public MusicPlayerOffCommand(MusicPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;
    }

    @Override
    public void execute() {
        musicPlayer.off();
    }

}
