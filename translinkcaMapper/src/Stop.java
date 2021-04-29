public class Stop {
    public int stopNumber;
    public String stopName;
    
    public Stop(int stopNumber, String stopName) {
        this.stopNumber = stopNumber;
        if(stopName.charAt(0) == 'W') {
            String sub = stopName.substring(9);
            sub += " WB";
            this.stopName = sub;
        }
        else if(stopName.charAt(0) == 'E'){
            String sub = stopName.substring(9);
            sub += " EB";
            this.stopName = sub;
        }
        else if(stopName.charAt(0) == 'N') {
            String sub = stopName.substring(10);
            sub += " NB";
            this.stopName = sub;
        }
        else if(stopName.charAt(0) == 'S') {
            String sub = stopName.substring(10);
            sub += " SB";
            this.stopName = sub;
        }
    }
}