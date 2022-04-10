public class Club extends Card {
    public Club(int value) {
        super(value);
        this.point = 0;
        if (value == 1) {
            point = 1;
        }
        if (value == 11) {
            point = 1;
        }
    }
    @Override
    public String toString() {
        if (this.value == 1 ) {
            return "\uD83C\uDCD1";
        }
        else if (this.value == 2) {
            return "\uD83C\uDCD2";
        }
        else if (this.value == 3) {
            return "\uD83C\uDCD3";
        }
        else if (this.value == 4) {
            return "\uD83C\uDCD4";
        }
        else if (this.value == 5) {
            return "\uD83C\uDCD5";
        }
        else if (this.value == 6) {
            return "\uD83C\uDCD6";
        }
        else if (this.value == 7) {
            return "\uD83C\uDCD7";
        }
        else if (this.value == 8) {
            return "\uD83C\uDCD8";
        }
        else if (this.value == 9) {
            return "\uD83C\uDCD9";
        }
        else if (this.value == 10) {
            return "\uD83C\uDCDA";
        }
        else if (this.value == 11) {
            return "\uD83C\uDCDB";
        }
        else if (this.value == 12) {
            return "\uD83C\uDCDD";
        }
        else if (this.value == 13) {
            return "\uD83C\uDCDE";
        }
        else {
            return ((Card)this).toString();
        }

    }
}
    