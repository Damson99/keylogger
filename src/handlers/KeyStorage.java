package handlers;

public class KeyStorage
{
    private int keyCode;
    private boolean pressed;
    private long systemsTimePressedMilled;


    public KeyStorage(int keyCode, boolean pressed, long systemsTimePressedMilled) {
        this.keyCode = keyCode;
        this.pressed = pressed;
        this.systemsTimePressedMilled = systemsTimePressedMilled;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public boolean isPressed() {
        return pressed;
    }

    @Override
    public String toString() {
        return "KeyStorage{" +
                "keyCode=" + keyCode +
                ", pressed=" + pressed +
                ", systemsTimePressedMilled=" + systemsTimePressedMilled +
                '}';
    }
}
