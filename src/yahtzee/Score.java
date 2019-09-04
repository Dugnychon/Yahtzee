package yahtzee;

import java.util.Arrays;

public class Score {
    private int[] tempScoreBoard = new int[16];
    private int[] scoreBoard = new int[16];


    public Score() {
        for (int i = 0; i < 16; i++) {
            tempScoreBoard[i] = 0;
        }
        for (int i = 0; i < 16; i++) {
            scoreBoard[i] = -1;
        }
    }

    public static String lower(int i) {

        switch (i) {
            case 1:
                return "Ones";
            case 2:
                return "Twos";
            case 3:
                return "Threes";
            case 4:
                return "Fours";
            case 5:
                return "Fives";
            case 6:
                return "Sixes";
            case 7:
                return "Bonus";
            case 8:
                return "Sum";
            case 9:
                return "Three of a kind";
            case 10:
                return "Four of a kind";
            case 11:
                return "Full House";
            case 12:
                return "Small straight";
            case 13:
                return "Large straight";
            case 14:
                return "Chance";
            case 15:
                return "Yahtzee";
            case 16:
                return "Total Score";
            default:
                return "0";
        }
    }

    public void maxScore(Dice[] dices) {
        // First reset score
        for (int diceValue = 0; diceValue < 16; diceValue++) {
            tempScoreBoard[diceValue] = 0;
        }

        upperScore(dices); // For score index 0 to 5
        tempScoreBoard[8] = threeOfKind(dices);
        tempScoreBoard[9] = fourOfKind(dices);
        tempScoreBoard[10] = fullhouse(dices);
        tempScoreBoard[11] = smallStraight(dices);
        tempScoreBoard[12] = largeStraight(dices);
        tempScoreBoard[13] = sumDices(dices);
        tempScoreBoard[14] = yahtzee(dices);


    }

    private void upperScore(Dice[] dices) {
        for (int diceValue = 0; diceValue < 6; diceValue++) {
            for (int value = 0; value < 5; value++) {
                if (dices[value].value() == diceValue + 1) {
                    tempScoreBoard[diceValue] += diceValue + 1;
                }
            }
        }
    }

    public void printMaxScore() {
        for (int i = 0; i < 16; i++) {
            System.out.printf("%-2.2s  %-16.16s  %-4.4s%n", i + 1, lower(i + 1), tempScoreBoard[i]);
        }
    }

    public void printScore() {
        for (int i = 0; i < 16; i++) {
            System.out.printf("%-2.2s  %-16.16s  %-4.4s%n", i + 1, lower(i + 1), scoreBoard[i]);
        }
    }

    public int[] getScore() {
        return this.scoreBoard;
    }

    public int[] getMaxScore() {
        return this.tempScoreBoard;
    }

    public boolean isAvailable(int scoreToCheck) {
        return scoreBoard[scoreToCheck - 1] == -1;
    }

    private void saveScore(int scorePos, int scoreValue) {
        scoreBoard[scorePos - 1] = scoreValue;
    }

    public void selectScore(Dice[] dices, int scorePos) {
        this.maxScore(dices);
        this.saveScore(scorePos, tempScoreBoard[scorePos - 1]);
    }


    protected int fullhouse(Dice[] dices) {
        int[] counts = new int[6];
        for (int i = 0; i < dices.length; i++)
        //increase the relevant counter
        {
            counts[dices[i].value() - 1]++;
        }
        //now check we've got a 2 and a 3
        boolean check2 = false;
        boolean check3 = false;
        for (int i : counts) {
            check2 |= (i == 2); //found 2 of some number
            check3 |= (i == 3); //found 3 of some number
            if (i == 5) return 25; //found a Yahtzee so stop and return true
        }
        return (check2 && check3) ? 25 : 0;
    }

    protected int threeOfKind(Dice[] dices) {
        System.out.println(dices);
        if ((dices[0].value() == dices[1].value() && dices[1].value() == dices[2].value()) ||
                (dices[1].value() == dices[2].value() && dices[2].value() == dices[3].value()) ||
                (dices[2].value() == dices[3].value() && dices[3].value() == dices[4].value())) {

            return sumDices(dices);
        } else {
            return 0;
        }
    }

    public int fourOfKind(Dice[] dices) {
        int counter = 0;

        int[] x = new int[6];
        for (int i = 0; i < dices.length; i++) {
            x[i] = (dices[i].value());
        }
        Arrays.sort(x);
        if ((x[0] == x[3]) || (x[1] == x[4]))
            return sumDices(dices);
        return 0;
    }

    public int smallStraight(Dice[] dices) {
        int counter = 0;

        int[] x = new int[6];
        for (int i = 0; i < dices.length; i++) {
            x[i] = (dices[i].value());
        }
        Arrays.sort(x);

        for (int i = 0; i < x.length - 1; i++) {
            if (x[i + 1] == x[i] + 1) {
                counter++;
            } else if (x[i + 1] == x[i]) {
                continue;
            } else {
                counter = 0;
            }
            if (counter == 3) {
                return 30;
            }
        }
        return 0;
    }

    public int largeStraight(Dice[] dices) {
        int counter = 0;

        int[] x = new int[6];
        for (int i = 0; i < dices.length; i++) {
            x[i] = (dices[i].value());
        }
        Arrays.sort(x);

        for (int i = 0; i < x.length - 1; i++) {
            if (x[i + 1] == x[i] + 1) {
                counter++;
            } else if (x[i + 1] == x[i]) {
                continue;
            } else {
                counter = 0;
            }
            if (counter == 4) {
                return 40;
            }
        }
        return 0;
    }


    private int sumDices(Dice[] dices) {
        int score = 0;
        for (int i = 0; i < dices.length; i++) {
            score = score + dices[i].value();
        }
        return score;
    }

    public int yahtzee(Dice[] dices) {
        int[] counts = new int[6];
        for (int i = 0; i < dices.length; i++) {
            counts[dices[i].value() - 1]++;
        }

        boolean check5 = false;
        for (int i : counts) {
            check5 |= (i == 5); //found 3 of some number
            if (i == 5) {
                return 50;
            }

        }
        return 0;

    }

}













