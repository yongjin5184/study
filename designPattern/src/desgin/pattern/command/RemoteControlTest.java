package desgin.pattern.command;

public class RemoteControlTest {
    public static void main(String[] args) {
        //invoker
        SimpleRemoteControl remote = new SimpleRemoteControl();

        //receiver
        Light light = new Light();

        //command
        LightOnCommand lightOn = new LightOnCommand(light);

        remote.setCommand(lightOn);
        remote.buttonPressed();

        //command
        LightOffCommand lightOff = new LightOffCommand(light);

        remote.setCommand(lightOff);
        remote.buttonPressed();

        //receiver
        MusicPlayer musicPlayer = new MusicPlayer();

        //command
        MusicPlayerOnCommand musicPlayerOn = new MusicPlayerOnCommand(musicPlayer);

        remote.setCommand(musicPlayerOn);
        remote.buttonPressed();

        //command
        MusicPlayerOffCommand musicPlayerOff = new MusicPlayerOffCommand(musicPlayer);

        remote.setCommand(musicPlayerOff);
        remote.buttonPressed();
    }
}
