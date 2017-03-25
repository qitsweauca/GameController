package data.values;

/**
 * Created by rkessler on 2017-03-25.
 */
public enum GameTypes implements DocumentableHtml {

    ROUNDROBIN(0, "round robin"),
    PLAYOFF(1, "playoff"),
    DROPIN(2, "drop-in");

    private byte byte_value;
    private String humanReadable;

    GameTypes(int byte_value, String humanReadable) {

        this.byte_value = (byte) byte_value;
        this.humanReadable = humanReadable;
    }

    public byte value() {
        return byte_value;
    }

    public static GameTypes fromValue(byte b) {
        for(GameTypes gameType : GameTypes.values()){
            if (gameType.value() == b){
                return gameType;
            }
        }
        System.out.println("This should never happen! Could not resolve GameType byte.");
        return null;
    }

    public String toString(){
        return this.humanReadable;
    }

    public String toDocumentation(){
        return String.format("<tr><td>%s</td><td>%s</td></tr>\n", this.humanReadable, this.byte_value);
    }
}
